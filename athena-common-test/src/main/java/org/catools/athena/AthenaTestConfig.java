package org.catools.athena;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.zaxxer.hikari.HikariDataSource;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.common.utils.JacksonUtil;
import org.catools.athena.core.feign.EnvironmentFeignClient;
import org.catools.athena.core.feign.ProjectFeignClient;
import org.catools.athena.core.feign.UserFeignClient;
import org.catools.athena.core.feign.VersionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.ContainerState;
import org.testcontainers.containers.wait.strategy.Wait;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
@ComponentScan({"org.catools.athena"})
@PropertySource("classpath:application.properties")
public class AthenaTestConfig {

  private static final String[] COMPOSE_CANDIDATES = {
      "docker/core-test-compose.yml",
      "docker/core-compose.yml"
  };

  private static final String ATHENA_DB = "athena-db";
  private static final String SERVICE_NAME = "athena-core";
  private static final int CORE_SERVICE_PORT = 8081;

  @Value("${athena.schema.name}")
  private String currentSchema;

  @Bean
  @ServiceConnection
  @SuppressWarnings("all")
  public ComposeContainer athenaApi() {
    Path dockerPath = findComposeFile();

    if (dockerPath == null) {
      throw new IllegalStateException("Could not find an Athena core compose file in project hierarchy");
    }

    return new ComposeContainer(dockerPath.toFile())
        .withExposedService(ATHENA_DB, 5432)
        .withExposedService(SERVICE_NAME, CORE_SERVICE_PORT)
        .waitingFor(ATHENA_DB, Wait.forLogMessage(".*database system is ready to accept connections.*", 1)
            .withStartupTimeout(Duration.of(120L, ChronoUnit.SECONDS)))
        .waitingFor(SERVICE_NAME, Wait.forHealthcheck()
            .withStartupTimeout(Duration.of(120L, ChronoUnit.SECONDS)));
  }

  @Bean
  @Profile("testContainers")
  public DataSource dataSource(ComposeContainer athenaApi) {
    ContainerState postgres = athenaApi.getContainerByServiceName(ATHENA_DB).orElseThrow();
    String host = postgres.getHost();
    Integer port = postgres.getMappedPort(5432);

    var dataSource = new HikariDataSource();
    dataSource.setJdbcUrl("jdbc:postgresql://%s:%s/athena?currentSchema=%s".formatted(host, port, currentSchema));
    dataSource.setUsername("postgres");
    dataSource.setPassword("password");
    return dataSource;
  }

  @Bean
  @Profile("testContainers")
  public ProjectFeignClient projectFeignClient(ComposeContainer athenaApi) {
    return coreFeignBuilder(athenaApi, ProjectFeignClient.class);
  }

  @Bean
  @Profile("testContainers")
  public UserFeignClient userFeignClient(ComposeContainer athenaApi) {
    return coreFeignBuilder(athenaApi, UserFeignClient.class);
  }

  @Bean
  @Profile("testContainers")
  public EnvironmentFeignClient environmentFeignClient(ComposeContainer athenaApi) {
    return coreFeignBuilder(athenaApi, EnvironmentFeignClient.class);
  }

  @Bean
  @Profile("testContainers")
  public VersionFeignClient versionFeignClient(ComposeContainer athenaApi) {
    return coreFeignBuilder(athenaApi, VersionFeignClient.class);
  }

  public <T> T coreFeignBuilder(ComposeContainer athenaApi, Class<T> apiType) {
    ContainerState athenaCore = athenaApi.getContainerByServiceName(SERVICE_NAME).orElseThrow();

    // for profiles where client defined url as annotation
    System.setProperty("feign.clients.athena.core.url", "http://%s:%s".formatted(athenaCore.getHost(), athenaCore.getMappedPort(CORE_SERVICE_PORT)));

    // for profiles where client defines programmatically
    return FeignUtils.defaultBuilder(apiType,
        JacksonUtil.objectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES),
        athenaCore.getHost(),
        athenaCore.getMappedPort(CORE_SERVICE_PORT));
  }

  private static Path findComposeFile() {
    Path currentPath = Path.of(".").toAbsolutePath();

    while (currentPath != null) {
      for (String composeCandidate : COMPOSE_CANDIDATES) {
        Path candidatePath = currentPath.resolve(composeCandidate);
        if (candidatePath.toFile().exists()) {
          return candidatePath;
        }
      }
      currentPath = currentPath.getParent();
    }

    return null;
  }
}
