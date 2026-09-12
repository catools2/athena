package org.catools.athena.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

/**
 * Who is asking. The gateway is the only thing in this system that knows.
 *
 * <p><b>Why here and nowhere else.</b> Athena's services sit on a private network and take no
 * credential. Métis's own auth is deliberately single-operator — <i>"No sessions, no refresh, no
 * user database, no password reset… a bearer token checked against a file is proportionate to
 * that, and anything more would be a second authentication system to keep correct"</i>. A product
 * for a team needs identity, and adding it to Métis is the move that file argues against. So the
 * gateway owns it, which also removes CORS from the problem entirely: everything the browser talks
 * to is one origin.
 *
 * <p><b>What this does not do.</b> It does not put the caller's identity on the wire to Métis as a
 * credential. Métis authenticates one service token held here, and a decision taken under it would
 * be audited as that service account rather than as a person — which is why the route table
 * forwards no decision at all, and why the identity attached in {@link MetisActorFilter} travels
 * as an observability header rather than as an authorisation.
 */
@Configuration
@EnableWebFluxSecurity
public class GatewaySecurityConfig {

  /** Where a sign-in starts. Spring derives it from the registration id in `application.yml`. */
  private static final String LOGIN_PATH = "/oauth2/authorization/athena";

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(
      ServerHttpSecurity http, ReactiveClientRegistrationRepository clients) {
    return http.authorizeExchange(exchange -> exchange
            // A load balancer is not a principal. Liveness and readiness only - the rest of
            // actuator is not exposed at all (see `management.endpoints.web.exposure`).
            .pathMatchers("/actuator/health", "/actuator/health/**").permitAll()
            // Everything else, including the app shell itself. There is no anonymous view of
            // Athena: the graph carries a customer's requirements and the code recovered from
            // their services, and read-only has never meant public.
            .anyExchange().authenticated())
        .oauth2Login(Customizer.withDefaults())
        .logout(logout -> logout.logoutSuccessHandler(rpInitiatedLogout(clients)))
        // The browser holds a session cookie, so a cross-site form post would otherwise be
        // authenticated. `withHttpOnlyFalse` is deliberate: the SPA has to read the cookie to
        // echo it back in a header, which is the whole mechanism.
        .csrf(csrf -> csrf.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()))
        .exceptionHandling(handling ->
            handling.authenticationEntryPoint(browserRedirectButApiGets401()))
        .build();
  }

  /**
   * A 302 to the identity provider is the right answer for a person and the wrong one for a fetch.
   *
   * <p>An expired session would otherwise turn every panel's request into a redirect the browser
   * follows to a login page, and the SPA would parse Keycloak's HTML as its JSON and report a
   * parse error — the failure mode where "you are signed out" reads as "the service is broken".
   * A request that did not ask for HTML gets 401 and the app can say so plainly.
   */
  private ServerAuthenticationEntryPoint browserRedirectButApiGets401() {
    RedirectServerAuthenticationEntryPoint toIdentityProvider =
        new RedirectServerAuthenticationEntryPoint(LOGIN_PATH);

    return (exchange, denied) -> {
      var accept = exchange.getRequest().getHeaders().getAccept();
      boolean wantsHtml = accept.stream().anyMatch(MediaType.TEXT_HTML::isCompatibleWith);
      if (wantsHtml) {
        return toIdentityProvider.commence(exchange, denied);
      }
      var response = exchange.getResponse();
      response.setStatusCode(HttpStatus.UNAUTHORIZED);
      // A challenge that names the mechanism AND the door. Métis answers its own 401 with
      // `WWW-Authenticate: Bearer`, and that one means the service token this gateway holds was
      // refused - an operator's problem, not the reader's. Two 401s that mean opposite things
      // must not arrive at the SPA looking identical, so this one says what it is and where to go.
      response.getHeaders().set(HttpHeaders.WWW_AUTHENTICATE,
          "Session realm=\"athena\", login=\"" + LOGIN_PATH + "\"");
      return response.setComplete();
    };
  }

  /**
   * Signing out here signs out at the provider too.
   *
   * <p>Dropping only the local session leaves the next visit silently signed straight back in,
   * which looks like a logout that did not work and is worse than not offering one.
   */
  private ServerLogoutSuccessHandler rpInitiatedLogout(
      ReactiveClientRegistrationRepository clients) {
    var handler = new OidcClientInitiatedServerLogoutSuccessHandler(clients);
    handler.setPostLogoutRedirectUri("{baseUrl}/ui/");
    return handler;
  }

  /**
   * Makes the CSRF cookie actually get written.
   *
   * <p>In WebFlux the token is deferred: nothing subscribes to it on a plain GET, so the cookie is
   * never set, and the SPA's first POST then fails with a missing token it had no way to obtain.
   * Subscribing here on every request is the documented remedy.
   */
  @Bean
  public WebFilter csrfCookieWebFilter() {
    return (exchange, chain) -> {
      Mono<CsrfToken> token = exchange.getAttribute(CsrfToken.class.getName());
      return token == null ? chain.filter(exchange) : token.then(chain.filter(exchange));
    };
  }
}
