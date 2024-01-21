package org.catools.athena.pact.consumer.core;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import org.catools.athena.core.model.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.catools.athena.pact.consumer.core.CorePactTestConstants.*;
import static org.catools.athena.rest.core.config.CorePathDefinitions.ROOT_API;
import static org.catools.athena.rest.core.config.CorePathDefinitions.USER_PATH;

@ExtendWith(PactConsumerTestExt.class)
@Provider(USER_PROVIDER_NAME)
@PactFolder(PACT_FOLDER)
public class UserControllerPactConsumerTest {

    @SuppressWarnings("unused")
    @Pact(provider = USER_PROVIDER_NAME, consumer = USER_CONSUMER_NAME)
    public V4Pact getUserById(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        DslPart body = new PactDslJsonBody()
            .integerType("id")
            .stringType("name");

        return builder
            .given("GetUserById")
            .uponReceiving("GET REQUEST")
            .path(ROOT_API + USER_PATH + "/1")
            .method("GET")
            .willRespondWith()
            .status(200)
            .headers(headers)
            .body(body)
            .toPact()
            .asV4Pact()
            .get();
    }

    @Test
    @PactTestFor(providerName = USER_PROVIDER_NAME, pactMethod = "getUserById")
    void givenGet_whenSendRequest_shouldReturn200WithProperHeaderAndBody(MockServer mockServer) {
        // when
        ResponseEntity<UserDto> response = new RestTemplate().getForEntity(mockServer.getUrl() + ROOT_API + USER_PATH + "/1", UserDto.class);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getHeaders().getOrEmpty("Content-Type").contains("application/json")).isTrue();
    }
}
