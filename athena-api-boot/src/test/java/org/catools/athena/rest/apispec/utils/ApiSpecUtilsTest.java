package org.catools.athena.rest.apispec.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.tuple.Pair;
import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.rest.AthenaBaseTest;
import org.catools.athena.rest.apispec.service.ApiSpecService;
import org.catools.athena.rest.core.builder.CoreBuilder;
import org.catools.athena.rest.core.service.ProjectService;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiSpecUtilsTest extends AthenaBaseTest {

    static ProjectDto PROJECT_DTO;

    @Autowired
    ApiSpecUtils apiSpecUtils;

    @Autowired
    ProjectService projectService;

    @Autowired
    ApiSpecService apiSpecService;

    @BeforeAll
    public void beforeAll() {
        PROJECT_DTO = CoreBuilder.buildProjectDto();
        PROJECT_DTO.setId(projectService.save(PROJECT_DTO).getId());
    }

    @Test
    @Order(1)
    void shallSaveOriginalSpecWhenSpecInformationIsProvided() throws IOException {
        File resource = ResourceUtils.getFile("src/test/resources/testdata/openApiSpec.json");
        JsonElement openApiSpec = JsonParser.parseString(Files.readString(resource.toPath()));

        // Save spec with one tag
        Pair<ApiSpecDto, Set<ApiPathDto>> response = apiSpecUtils.saveOpenApiSpec(openApiSpec, "OpenApi", PROJECT_DTO.getCode());
        verifyOriginalSpec(response);
    }

    @Test
    @Order(2)
    void shallUpdateExistingSpecRecordIfSpecAlreadyExists() throws IOException {
        Optional<ApiSpecDto> apiSpecDto1 = apiSpecService.getApiSpecByProjectCodeAndName(PROJECT_DTO.getCode(), "OpenApi");
        assertThat(apiSpecDto1.isPresent(), equalTo(true));

        File resource = ResourceUtils.getFile("src/test/resources/testdata/openApiSpec.json");
        JsonElement openApiSpec = JsonParser.parseString(Files.readString(resource.toPath()));
        JsonObject newTag = new JsonObject();
        newTag.addProperty("name", "P2");
        openApiSpec.getAsJsonObject().get("tags").getAsJsonArray().add(newTag);
        openApiSpec.getAsJsonObject().get("paths").getAsJsonObject().remove("/athena/api/scenario");

        Pair<ApiSpecDto, Set<ApiPathDto>> response = apiSpecUtils.saveOpenApiSpec(openApiSpec, "OpenApi", PROJECT_DTO.getCode());
        verifyModifiedSpec(response, apiSpecDto1.get());
    }

    @Test
    @Order(3)
    void shallRemoveTagRelationshipFromExistingSpecRecordIfTagRemovedFromSpec() throws IOException {
        Optional<ApiSpecDto> apiSpecDto1 = apiSpecService.getApiSpecByProjectCodeAndName(PROJECT_DTO.getCode(), "OpenApi");
        assertThat(apiSpecDto1.isPresent(), equalTo(true));

        File resource = ResourceUtils.getFile("src/test/resources/testdata/openApiSpec.json");
        JsonElement openApiSpec = JsonParser.parseString(Files.readString(resource.toPath()));
        Pair<ApiSpecDto, Set<ApiPathDto>> response = apiSpecUtils.saveOpenApiSpec(openApiSpec, "OpenApi", PROJECT_DTO.getCode());
        verifyOriginalSpec(response);
    }

    public static void verifyModifiedSpec(final Pair<ApiSpecDto, Set<ApiPathDto>> response, final ApiSpecDto apiSpecDto1) {
        ApiSpecDto apiSpecDto2 = response.getKey();
        assertThat(apiSpecDto2.getVersion(), equalTo(apiSpecDto1.getVersion()));
        assertThat(apiSpecDto2.getName(), equalTo(apiSpecDto1.getName()));
        assertThat(apiSpecDto2.getTitle(), equalTo(apiSpecDto1.getTitle()));
        assertThat(apiSpecDto2.getProject(), equalTo(apiSpecDto1.getProject()));
        assertThat(apiSpecDto2.getMetadata(), notNullValue());
        assertThat(apiSpecDto2.getMetadata().size(), equalTo(2));
        assertThat(apiSpecDto2.getMetadata().stream().filter(t -> "Actuator".equals(t.getValue())).findFirst().map(MetadataDto::getName).orElse(""), equalTo("TAG"));
        assertThat(apiSpecDto2.getMetadata().stream().filter(t -> "P2".equals(t.getValue())).findFirst().map(MetadataDto::getName).orElse(""), equalTo("TAG"));
        assertThat(apiSpecDto2.getFirstTimeSeen(), equalTo(apiSpecDto1.getFirstTimeSeen()));
        assertThat(apiSpecDto2.getLastTimeSeen(), IsNot.not(equalTo(apiSpecDto1.getLastTimeSeen())));

        Set<ApiPathDto> apiPathDtos = response.getValue();
        assertThat(apiPathDtos, notNullValue());
        assertThat(apiPathDtos.size(), equalTo(2));
    }

    public static void verifyOriginalSpec(final Pair<ApiSpecDto, Set<ApiPathDto>> response) {
        ApiSpecDto apiSpecDto1 = response.getKey();
        assertThat(apiSpecDto1.getVersion(), equalTo("V30"));
        assertThat(apiSpecDto1.getName(), equalTo("OpenApi"));
        assertThat(apiSpecDto1.getTitle(), equalTo("OpenAPI definition"));
        assertThat(apiSpecDto1.getProject(), equalTo(PROJECT_DTO.getCode()));
        assertThat(apiSpecDto1.getFirstTimeSeen(), notNullValue());
        assertThat(apiSpecDto1.getLastTimeSeen(), notNullValue());
        assertThat(apiSpecDto1.getMetadata(), notNullValue());
        assertThat(apiSpecDto1.getMetadata().stream().findFirst().isPresent(), equalTo(true));
        assertThat(apiSpecDto1.getMetadata().size(), equalTo(1));
        assertThat(apiSpecDto1.getMetadata().stream().findFirst().get().getName(), equalTo("TAG"));
        assertThat(apiSpecDto1.getMetadata().stream().findFirst().get().getValue(), equalTo("Actuator"));

        Set<ApiPathDto> apiPathDtos = response.getValue();
        assertThat(apiPathDtos, notNullValue());
        assertThat(apiPathDtos.size(), equalTo(3));
    }
}