package org.catools.athena;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.zaxxer.hikari.HikariDataSource;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.common.utils.JacksonUtil;
import org.catools.athena.core.feign.EnvironmentFeignClient;
import org.catools.athena.core.feign.ProjectFeignClient;
import org.catools.athena.core.feign.UserFeignClient;
import org.catools.athena.core.feign.VersionFeignClient;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.testcontainers.containers.ContainerState;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
@ComponentScan({"org.catools.athena"})
@PropertySource("classpath:application.properties")
public class AthenaTestConfig {

  private static final String ATHENA_DB = "athena-db";
  private static final String SERVICE_NAME = "athena-core";
  private static final int CORE_SERVICE_PORT = 8081;

  @Bean
  @ServiceConnection
  @SuppressWarnings("all")
  public DockerComposeContainer<?> athenaApi() {
    Path projectRoot = Path.of(".").toAbsolutePath().getParent().getParent().toAbsolutePath();
    return new DockerComposeContainer<>(new File(projectRoot + "/docker/core-compose.yml"))
        .withExposedService(ATHENA_DB, 5432)
        .withExposedService(SERVICE_NAME, CORE_SERVICE_PORT)
        .waitingFor(ATHENA_DB, Wait.forLogMessage(".*database system is ready to accept connections.*",1)
            .withStartupTimeout(Duration.of(60L, ChronoUnit.SECONDS)))
        .waitingFor(SERVICE_NAME, Wait.forHealthcheck()
            .withStartupTimeout(Duration.of(120L, ChronoUnit.SECONDS)));
  }

  @Bean
  @Profile("testContainers")
  public DataSource dataSource(DockerComposeContainer<?> athenaApi) {
    ContainerState postgres = athenaApi.getContainerByServiceName(ATHENA_DB).orElseThrow();
    String host = postgres.getHost();
    Integer port = postgres.getMappedPort(5432);

    var dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(String.format("jdbc:postgresql://%s:%s/athena?currentSchema=athena_core", host, port));
    dataSource.setUsername("postgres");
    dataSource.setPassword("password");
    return dataSource;
  }

  @Bean
  @Profile("testContainers")
  public ProjectFeignClient projectFeignClient(DockerComposeContainer<?> athenaApi) {
    return coreFeignBuilder(athenaApi, ProjectFeignClient.class);
  }

  @Bean
  @Profile("testContainers")
  public UserFeignClient userFeignClient(DockerComposeContainer<?> athenaApi) {
    return coreFeignBuilder(athenaApi, UserFeignClient.class);
  }

  @Bean
  @Profile("testContainers")
  public EnvironmentFeignClient environmentFeignClient(DockerComposeContainer<?> athenaApi) {
    return coreFeignBuilder(athenaApi, EnvironmentFeignClient.class);
  }

  @Bean
  @Profile("testContainers")
  public VersionFeignClient versionFeignClient(DockerComposeContainer<?> athenaApi) {
    return coreFeignBuilder(athenaApi, VersionFeignClient.class);
  }

  public <T> T coreFeignBuilder(DockerComposeContainer<?> athenaApi, Class<T> apiType) {
    ContainerState athenaCore = athenaApi.getContainerByServiceName(SERVICE_NAME).orElseThrow();

    // for profiles where client defined url as annotation
    System.setProperty("feign.clients.athena.core.url", "http://%s:%s".formatted(athenaCore.getHost(), athenaCore.getMappedPort(CORE_SERVICE_PORT)));

    // for profiles where client defines programmatically
    return FeignUtils.defaultBuilder(apiType,
        JacksonUtil.objectMapper().findAndRegisterModules().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES),
        athenaCore.getHost(),
        athenaCore.getMappedPort(CORE_SERVICE_PORT));
  }
}
