package org.catools.athena.gateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOidcLogin;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

/**
 * Identity at the gateway: who is refused, who is let through, and what Métis is told about them.
 *
 * <p>The three properties Phase D exists to establish, asserted rather than described:
 *
 * <ol>
 *   <li>an unauthenticated request to {@code /metis/**} is refused <b>at the gateway</b>;
 *   <li>a decision route is refused <b>by the allowlist</b> even holding a valid identity;
 *   <li>the credential Métis sees is the gateway's service token, and the person's name travels
 *       beside it as evidence rather than as authorisation.
 * </ol>
 */
@SpringBootTest
@AutoConfigureWebTestClient
class GatewaySecurityTest {

  private static final List<String> REACHED = new CopyOnWriteArrayList<>();

  private static final DisposableServer METIS = HttpServer.create()
      .port(0)
      .handle((request, response) -> {
        REACHED.add(request.method().name()
            + " " + request.fullPath()
            + " auth=" + request.requestHeaders().get("Authorization")
            + " actor=" + request.requestHeaders().get("X-Athena-Actor"));
        return response.header("content-type", "application/json")
            .sendString(reactor.core.publisher.Mono.just("{\"ok\":true}"));
      })
      .bindNow();

  @DynamicPropertySource
  static void metisIsHere(DynamicPropertyRegistry registry) {
    registry.add("metis.api.uri", () -> "http://127.0.0.1:" + METIS.port());
    registry.add("metis.api.token", () -> "service-token");
  }

  @AfterAll
  static void stopMetis() {
    METIS.disposeNow();
  }

  @Autowired private WebTestClient anonymous;

  @BeforeEach
  void forgetPreviousRequests() {
    REACHED.clear();
  }

  /** Signed in as `demo`, the way the realm's user arrives. */
  private WebTestClient signedIn() {
    return anonymous.mutateWith(mockOidcLogin().idToken(token -> token.claim("preferred_username", "demo")));
  }

  @Test
  void an_anonymous_read_never_reaches_metis() {
    anonymous.get()
        .uri("/metis/journeys")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isUnauthorized();

    // The point is not the status code. It is that the read was not performed.
    assertThat(REACHED).as("an anonymous request was forwarded to Métis").isEmpty();
  }

  @Test
  void a_person_is_sent_to_the_identity_provider_and_a_fetch_is_not() {
    // A browser asking for a page gets a door to walk through.
    anonymous.get()
        .uri("/ui/")
        .accept(MediaType.TEXT_HTML)
        .exchange()
        .expectStatus()
        .isFound()
        .expectHeader()
        .value(HttpHeaders.LOCATION, location ->
            assertThat(location).contains("/oauth2/authorization/athena"));

    // A panel's fetch gets an answer it can act on. Redirecting it would hand the SPA Keycloak's
    // HTML to parse as JSON, and "you are signed out" would surface as "the service is broken".
    anonymous.get()
        .uri("/analytics/queries")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isUnauthorized()
        .expectHeader()
        .doesNotExist(HttpHeaders.LOCATION)
        // Named, because the SPA has to tell this apart from Métis's own `Bearer` challenge,
        // which means the service token was refused and is nothing the reader can fix.
        .expectHeader()
        .value(HttpHeaders.WWW_AUTHENTICATE, challenge -> {
          assertThat(challenge).startsWith("Session ");
          assertThat(challenge).contains("login=\"/oauth2/authorization/athena\"");
        });
  }

  @Test
  void liveness_is_not_behind_the_login() {
    // A load balancer is not a principal, and a probe that 302s to Keycloak reads as a dead pod.
    anonymous.get().uri("/actuator/health").exchange().expectStatus().isOk();
  }

  @Test
  void a_decision_is_refused_by_the_allowlist_even_with_a_valid_identity() {
    // The assertion Phase D turns on. Being signed in is not the question: Métis audits decisions
    // against the identity that took them, and everything forwarded from here carries ONE service
    // token - so a decision taken through the gateway would name `gateway-service` no matter who
    // was signed in. The refusal is the route table's, not the login's.
    signedIn()
        .mutateWith(csrf())
        .post()
        .uri("/metis/publications/b1/confirm?literal=publish&fingerprint=fp")
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_FOUND);

    assertThat(REACHED).as("a decision request was forwarded to Métis").isEmpty();
  }

  @Test
  void metis_sees_the_service_token_and_is_told_who_asked() {
    signedIn().get().uri("/metis/queue").exchange().expectStatus().isOk();

    assertThat(REACHED).hasSize(1);
    // The credential is the gateway's. The name is evidence for the access log, and Métis resolves
    // its identity from the token alone - the header authorises nothing.
    assertThat(REACHED.get(0)).contains("auth=Bearer service-token");
    assertThat(REACHED.get(0)).endsWith("actor=demo");
  }

  @Test
  void a_client_cannot_name_itself() {
    // A header the gateway sets is a header a client must not be able to set. Without the strip,
    // this request would arrive at Métis claiming to be whoever the sender typed.
    signedIn()
        .get()
        .uri("/metis/queue")
        .header("X-Athena-Actor", "somebody-else")
        .exchange()
        .expectStatus()
        .isOk();

    assertThat(REACHED).hasSize(1);
    assertThat(REACHED.get(0)).endsWith("actor=demo");
    assertThat(REACHED.get(0)).doesNotContain("somebody-else");
  }

  @Test
  void an_anonymous_request_carries_no_actor_to_athenas_own_services() {
    // The strip is global, not a Métis special case: nothing upstream of the gateway may assert
    // an identity to anything downstream of it.
    anonymous.get()
        .uri("/analytics/queries")
        .accept(MediaType.APPLICATION_JSON)
        .header("X-Athena-Actor", "somebody-else")
        .exchange()
        .expectStatus()
        .isUnauthorized();

    assertThat(REACHED).isEmpty();
  }

  @Test
  void the_id_tokens_subject_is_used_when_the_provider_sends_no_username() {
    // Not every provider sends `preferred_username`. An empty actor header would be worse than a
    // subject: it reads as "nobody asked".
    anonymous
        .mutateWith(mockOidcLogin().idToken(token -> token.subject("7f3c-sub")))
        .get()
        .uri("/metis/queue")
        .exchange()
        .expectStatus()
        .isOk();

    assertThat(REACHED).hasSize(1);
    assertThat(REACHED.get(0)).endsWith("actor=7f3c-sub");
  }
}
