package org.catools.athena;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
@ComponentScan({"org.catools.athena"})
@PropertySource("classpath:application.properties")
public class SpringTestConfig {

  @Bean
  @ServiceConnection
  public PostgreSQLContainer<?> postgresContainer() {
    DockerImageName postgresImage = DockerImageName.parse("akeshmiri/athena-pg-demo:0.1")
        .asCompatibleSubstituteFor("postgres");

    return new PostgreSQLContainer<>(postgresImage)
        .withDatabaseName("athena")
        .withUsername("postgres")
        .withPassword("password")
        .waitingFor(new LogMessageWaitStrategy()
            .withRegEx(".*database system is ready to accept connections.*")
            .withTimes(1)
            .withStartupTimeout(Duration.of(60L, ChronoUnit.SECONDS)));
  }

}
