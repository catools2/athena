package org.catools.athena.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.autoconfigure.LifecycleMvcEndpointAutoConfiguration;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration;
import org.springframework.cloud.client.discovery.simple.reactive.SimpleReactiveDiscoveryClientAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerBeanPostProcessorAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerClientAutoConfiguration;
import org.springframework.cloud.commons.util.UtilAutoConfiguration;

@SpringBootApplication(
    scanBasePackages = "org.catools.athena",
    exclude = {
        LoadBalancerBeanPostProcessorAutoConfiguration.class,
        SimpleReactiveDiscoveryClientAutoConfiguration.class,
        LoadBalancerAutoConfiguration.class,
        ReactorLoadBalancerClientAutoConfiguration.class,
        LifecycleMvcEndpointAutoConfiguration.class,
        SimpleDiscoveryClientAutoConfiguration.class,
        UtilAutoConfiguration.class,
        RefreshAutoConfiguration.class
    }
)
public class AthenaGatewayApplication {
  public static void main(String[] args) {
    SpringApplication.run(AthenaGatewayApplication.class, args);
  }
}
