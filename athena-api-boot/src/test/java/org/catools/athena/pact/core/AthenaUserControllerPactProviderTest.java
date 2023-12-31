package org.catools.athena.pact.core;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import org.catools.athena.core.model.UserDto;
import org.catools.athena.rest.core.builder.AthenaCoreBuilder;
import org.catools.athena.rest.core.controller.AthenaCoreControllerTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.catools.athena.pact.core.AthenaCorePactTestConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = "server.port=8080")
@Provider(USER_PROVIDER_NAME)
@Consumer(USER_CONSUMER_NAME)
@PactFolder(PACT_FOLDER)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AthenaUserControllerPactProviderTest extends AthenaCoreControllerTest {

    private static UserDto USER_DTO;

    @BeforeAll
    public void beforeAll() {
        USER_DTO = AthenaCoreBuilder.buildUserDto();
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @Test
    @Order(1)
    @State("GetUserById")
    void getUser() {
        athenaUserController.saveUser(USER_DTO);
        ResponseEntity<UserDto> response = athenaUserController.getUserById(1L);
        assertThat(response.getStatusCode().value(), equalTo(200));
        assertThat(response.getBody(), notNullValue());
        assertThat(response.getBody().getName(), equalTo(USER_DTO.getName()));
    }
}
