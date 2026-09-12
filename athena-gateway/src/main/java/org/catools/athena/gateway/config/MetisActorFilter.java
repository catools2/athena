package org.catools.athena.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Names the person behind a Métis read, and makes sure nobody else can.
 *
 * <p><b>The header is evidence, not authorisation.</b> Métis authenticates the one service token
 * the route attaches and resolves its identity from that alone; this header is what makes the
 * gateway's own access log able to answer "who read the queue". It is deliberately NOT a way to
 * present a person to Métis: a decision taken under a forwarded identity would be audited against
 * the service account regardless, which is why the route table forwards no decision at all.
 *
 * <p><b>Stripped on the way in, on every route.</b> A header the gateway sets is a header a client
 * must not be able to set, and the failure if it could is not theoretical - it would be a request
 * that arrives naming whoever the sender chose. The inbound value is removed before the identity
 * is attached, so the two cannot be confused for each other.
 */
@Component
public class MetisActorFilter implements GlobalFilter, Ordered {

  /** Read by the access log, and by nothing that makes a decision. */
  public static final String ACTOR_HEADER = "X-Athena-Actor";

  /** The route in `application.yml`. One door to the reasoning plane, and this is its name. */
  private static final String METIS_ROUTE_ID = "metis-api";

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    var stripped = exchange.mutate()
        .request(request -> request.headers(headers -> headers.remove(ACTOR_HEADER)))
        .build();

    if (!isMetisRoute(stripped)) {
      return chain.filter(stripped);
    }

    // The exchange's own principal, not `ReactiveSecurityContextHolder`: the gateway hands each
    // filter a mutated exchange, and reading the identity from the object being routed carries no
    // assumption about which reactive context this filter is subscribed in.
    return stripped.getPrincipal()
        .filter(principal -> !(principal instanceof Authentication authentication)
            || authentication.isAuthenticated())
        .map(MetisActorFilter::nameOf)
        .map(actor -> stripped.mutate()
            .request(request -> request.headers(headers -> headers.set(ACTOR_HEADER, actor)))
            .build())
        // No identity means the request never reaches here in production - the security chain
        // refuses it first. Falling through with the header absent rather than inventing a value
        // keeps "unknown" and "anonymous" from becoming the same entry in a log.
        .defaultIfEmpty(stripped)
        .flatMap(chain::filter);
  }

  /**
   * Which route matched, not which path arrived.
   *
   * <p>This filter runs after {@code StripPrefix=1} has already rewritten the request, so by the
   * time it is called a Métis read is addressed {@code /queue} and a test asserting on
   * {@code /metis/} silently matches nothing. The route id is what actually identifies the
   * reasoning plane, and it survives every rewrite between here and the wire.
   */
  private static boolean isMetisRoute(ServerWebExchange exchange) {
    Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
    return route != null && METIS_ROUTE_ID.equals(route.getId());
  }

  private static String nameOf(java.security.Principal principal) {
    if (principal instanceof Authentication authentication
        && authentication.getPrincipal() instanceof OidcUser user) {
      var preferred = user.getPreferredUsername();
      // Not every provider sends `preferred_username`. The subject always exists, and a subject
      // in the log beats an empty header, which reads as "nobody asked".
      return preferred != null ? preferred : user.getSubject();
    }
    return principal.getName();
  }

  /**
   * Before the routing filter, which is what sends the request on. Anything later would mutate a
   * request that has already left.
   */
  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE - 100;
  }
}
