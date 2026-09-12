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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

/**
 * What the gateway may forward to Métis, and under whose credential.
 *
 * <p><b>Why this file exists.</b> The rule it guards is written down in three places and, until
 * now, enforced by none: <em>the gateway may forward reads, and never a decision.</em> Métis gates
 * its decisions on an identity that must appear in the audit record, and everything forwarded from
 * here carries one service token — so an approval taken through this gateway would be recorded as
 * `gateway-service` for every approver, destroying both the audit trail and the rule that a
 * proposer may not approve their own work.
 *
 * <p>A YAML predicate is easy to widen by accident. These tests assert the consequence rather than
 * the configuration: a decision request must not reach Métis at all, and the credential Métis sees
 * must be the gateway's own regardless of what the browser sent.
 *
 * <p>Métis is stubbed by a real HTTP server on a random port, not mocked: what is under test is the
 * route table, and a route table is only exercised by an actual proxied request.
 */
@SpringBootTest
@AutoConfigureWebTestClient
class MetisRouteTest {

  /** Every request that actually reached "Métis", as `METHOD /path` with the credential seen. */
  private static final List<String> REACHED = new CopyOnWriteArrayList<>();

  private static final DisposableServer METIS = HttpServer.create()
      .port(0)
      .handle((request, response) -> {
        REACHED.add(request.method().name()
            + " " + request.fullPath()
            + " auth=" + request.requestHeaders().get("Authorization"));
        return response.header("content-type", "application/json").sendString(
            reactor.core.publisher.Mono.just("{\"ok\":true}"));
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

  private WebTestClient client;

  /**
   * Signed in, because every route now requires it. The subject of this file is what the ROUTE
   * table does with a request it accepts; refusing an anonymous one is {@link GatewaySecurityTest}.
   */
  @BeforeEach
  void signIn(@Autowired WebTestClient unauthenticated) {
    // `csrf()` so that a refused write is refused by the ROUTE TABLE rather than by the CSRF
    // filter in front of it. Without a token every POST here is a 403 and the allowlist this
    // file exists to guard is never consulted - the test would pass with the route deleted.
    client = unauthenticated.mutateWith(mockOidcLogin()).mutateWith(csrf());
    REACHED.clear();
  }

  @Test
  void a_read_is_forwarded_with_its_prefix_stripped() {
    client.get().uri("/metis/journeys").exchange().expectStatus().isOk();

    assertThat(REACHED).hasSize(1);
    // StripPrefix=1: Métis serves `/journeys`, and the `/metis` segment is this gateway's own
    // namespace rather than part of the API it fronts.
    assertThat(REACHED.get(0)).startsWith("GET /journeys");
  }

  @Test
  void a_decision_never_reaches_metis() {
    // The assertion this file is for. Not "is refused with a helpful message" — a POST to a gate
    // must not be forwarded AT ALL, because forwarding it is what puts the service token's name
    // in an audit record that should carry a person's.
    client.post()
        .uri("/metis/publications/b1/confirm?literal=publish&fingerprint=fp")
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.NOT_FOUND);

    assertThat(REACHED).as("a decision request was forwarded to Métis").isEmpty();
  }

  @Test
  void no_write_verb_is_forwarded_even_to_a_read_path() {
    // The allowlist is on the method, not on a list of decision paths — a path list is a thing to
    // keep in step with Métis, and it would be wrong the day Métis adds a gate.
    for (var request : List.of(
        client.post().uri("/metis/journeys"),
        client.put().uri("/metis/journeys"),
        client.patch().uri("/metis/journeys"),
        client.delete().uri("/metis/journeys"))) {
      request.exchange().expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    assertThat(REACHED).isEmpty();
  }

  @Test
  void the_browsers_own_credential_is_replaced_rather_than_forwarded_alongside() {
    // `SetRequestHeader`, not `AddRequestHeader`. Métis authenticates the first credential it is
    // given; a forwarded browser header could otherwise present an identity the gateway never
    // vouched for, and two Authorization headers is a question about which one wins.
    client.get()
        .uri("/metis/queue")
        .header("Authorization", "Bearer forged-by-the-browser")
        .exchange()
        .expectStatus()
        .isOk();

    assertThat(REACHED).hasSize(1);
    assertThat(REACHED.get(0)).endsWith("auth=Bearer service-token");
    assertThat(REACHED.get(0)).doesNotContain("forged-by-the-browser");
  }

  @Test
  void metis_is_not_reachable_under_any_other_prefix() {
    // The route is the only door. A read that arrives without the `/metis` prefix is not a Métis
    // read that lost its way — it is a request for one of Athena's services, and answering it from
    // the reasoning plane would be a different kind of mix-up entirely.
    client.get().uri("/journeys").exchange().expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(REACHED).isEmpty();
  }
}
