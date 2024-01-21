package org.catools.athena.rest.apispec.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.rest.apispec.entity.*;
import org.catools.athena.rest.apispec.mapper.ApiSpecMapper;
import org.catools.athena.rest.apispec.repository.*;
import org.catools.athena.rest.common.exception.ProjectNotFoundException;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.core.repository.ProjectRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ApiSpecUtils {
    private static final String TAG_METADATA_NAME = "TAG";

    private final ApiSpecMetadataRepository apiSpecMetadataRepository;
    private final ApiPathMetadataRepository apiPathMetadataRepository;
    private final ApiParameterRepository apiParameterRepository;
    private final ApiPathRepository apiPathRepository;
    private final ApiSpecRepository apiSpecRepository;
    private final ProjectRepository projectRepository;

    private final ApiSpecMapper apiSpecMapper;

    public Pair<ApiSpecDto, Set<ApiPathDto>> saveOpenApiSpec(JsonElement openAPISpec, String specName, String projectCode)
            throws ProjectNotFoundException {
        Project projectFromDB = projectRepository.findByCode(projectCode)
                .orElseThrow(() -> new ProjectNotFoundException(projectCode));

        OpenAPI openAPI = new Gson().fromJson(openAPISpec, OpenAPI.class);
        ApiSpec savedApiSpec = saveApiSpec(openAPI, specName, projectFromDB);

        Set<ApiPathDto> savedApiPaths = new HashSet<>();

        openAPI.getPaths().forEach((url, pathItem) -> savedApiPaths.addAll(saveApiPath(url, pathItem, savedApiSpec)));

        ApiSpecDto savedApiSpecDto = apiSpecMapper.apiSpecToApiSpecDto(savedApiSpec);
        return Pair.of(savedApiSpecDto, savedApiPaths);
    }

    public void normalizeApiSpecMetadata(ApiSpec apiSpec) {
        final Set<ApiSpecMetadata> metadata = new HashSet<>();
        for (ApiSpecMetadata md : apiSpec.getMetadata()) {
            // Read md from DB and if MD does not exist we create one and assign it to the pipeline
            ApiSpecMetadata apiSpecMetadata =
                    apiSpecMetadataRepository.findByNameAndValue(md.getName(), md.getValue())
                            .orElseGet(() -> apiSpecMetadataRepository.saveAndFlush(md));
            metadata.add(apiSpecMetadata);
        }
        apiSpec.setMetadata(metadata);
    }

    public void normalizeApiPathMetadata(ApiPath apiPath) {
        final Set<ApiPathMetadata> metadata = new HashSet<>();
        for (ApiPathMetadata md : apiPath.getMetadata()) {
            // Read md from DB and if MD does not exist we create one and assign it to the pipeline
            ApiPathMetadata apiPathMetadata =
                    apiPathMetadataRepository.findByNameAndValue(md.getName(), md.getValue())
                            .orElseGet(() -> apiPathMetadataRepository.saveAndFlush(md));
            metadata.add(apiPathMetadata);
        }
        apiPath.setMetadata(metadata);
    }

    public void normalizeApiPathParameter(ApiPath apiPath) {
        final Set<ApiParameter> metadata = new HashSet<>();
        for (ApiParameter md : apiPath.getParameters()) {
            // Read md from DB and if MD does not exist we create one and assign it to the pipeline
            ApiParameter apiParameter =
                    apiParameterRepository.findByNameAndType(md.getName(), md.getType())
                            .orElseGet(() -> apiParameterRepository.saveAndFlush(md));
            metadata.add(apiParameter);
        }
        apiPath.setParameters(metadata);
    }

    private ApiSpec saveApiSpec(OpenAPI openAPI, String specName, Project project) {
        ApiSpec apiSpecToSave = apiSpecRepository.findByProjectCodeAndName(project.getCode(), specName)
                .orElse(new ApiSpec());

        apiSpecToSave.setName(specName);
        apiSpecToSave.setProject(project);
        apiSpecToSave.setVersion(openAPI.getSpecVersion().name());
        apiSpecToSave.setTitle(openAPI.getInfo().getTitle());

        if (apiSpecToSave.getFirstTimeSeen() == null)
            apiSpecToSave.setFirstTimeSeen(Instant.now());
        apiSpecToSave.setLastTimeSeen(Instant.now());

        if (openAPI.getTags() == null) {
            apiSpecToSave.getMetadata().removeIf(m -> TAG_METADATA_NAME.equals(m.getName()));
        } else {
            // remove relationship for all values which are in DB but not in model to save
            apiSpecToSave.getMetadata().removeIf(t1 -> openAPI.getTags().stream().noneMatch(t2 -> compareApiSpecTag(t1, t2)));
            // Add new tags if not already exists
            for (Tag tag : openAPI.getTags()) {
                if (apiSpecToSave.getMetadata().stream().noneMatch(t1 -> compareApiSpecTag(t1, tag)))
                    apiSpecToSave.addMetadata(TAG_METADATA_NAME, tag.getName());
            }
            normalizeApiSpecMetadata(apiSpecToSave);
        }

        return apiSpecRepository.saveAndFlush(apiSpecToSave);
    }

    private Set<ApiPathDto> saveApiPath(String url, PathItem pathItem, ApiSpec apiSpec) {
        Set<ApiPathDto> savedApiPaths = new HashSet<>();

        if (pathItem.getGet() != null)
            savedApiPaths.add(saveApiPathOperation(url, "GET", pathItem.getGet(), apiSpec));

        if (pathItem.getPut() != null)
            savedApiPaths.add(saveApiPathOperation(url, "PUT", pathItem.getPut(), apiSpec));

        if (pathItem.getPost() != null)
            savedApiPaths.add(saveApiPathOperation(url, "POST", pathItem.getPost(), apiSpec));

        if (pathItem.getDelete() != null)
            savedApiPaths.add(saveApiPathOperation(url, "DELETE", pathItem.getDelete(), apiSpec));

        if (pathItem.getOptions() != null)
            savedApiPaths.add(saveApiPathOperation(url, "OPTIONS", pathItem.getOptions(), apiSpec));

        if (pathItem.getHead() != null)
            savedApiPaths.add(saveApiPathOperation(url, "HEAD", pathItem.getHead(), apiSpec));

        if (pathItem.getPatch() != null)
            savedApiPaths.add(saveApiPathOperation(url, "PATCH", pathItem.getPatch(), apiSpec));

        if (pathItem.getTrace() != null)
            savedApiPaths.add(saveApiPathOperation(url, "TRACE", pathItem.getTrace(), apiSpec));

        return savedApiPaths;
    }

    private ApiPathDto saveApiPathOperation(String url, String method, Operation operation, ApiSpec apiSpec) {
        ApiPath apiPath = apiPathRepository.findByApiSpecIdAndMethodAndUrl(apiSpec.getId(), method, url).orElse(new ApiPath());

        apiPath.setMethod(method);
        apiPath.setTitle(operation.getSummary());
        apiPath.setDescription(operation.getDescription());
        apiPath.setUrl(url);
        apiPath.setApiSpec(apiSpec);

        // remove relationship for all values which are in DB but not in model to save
        if (operation.getParameters() == null) {
            apiPath.getParameters().clear();
        } else {
            apiPath.getParameters().removeIf(t1 -> operation.getParameters().stream().noneMatch(t2 -> compareApiParameter(t1, t2)));
            // Add new parameter if not already exists
            for (Parameter param : operation.getParameters()) {
                if (apiPath.getParameters().stream().noneMatch(t1 -> compareApiParameter(t1, param)))
                    apiPath.addParameter(param.getSchema().getType(), param.getName());
            }

            normalizeApiPathParameter(apiPath);
        }

        if (operation.getTags() == null) {
            apiPath.getMetadata().removeIf(m -> TAG_METADATA_NAME.equals(m.getName()));
        } else {
            // remove relationship for all values which are in DB but not in model to save
            apiPath.getMetadata().removeIf(t1 -> operation.getTags().stream().noneMatch(t2 -> compareApiPathTag(t1, t2)));
            // Add new tags if not already exists
            for (String tag : operation.getTags()) {
                if (apiPath.getMetadata().stream().noneMatch(t1 -> compareApiPathTag(t1, tag)))
                    apiPath.addMetadata(TAG_METADATA_NAME, tag);
            }
            normalizeApiPathMetadata(apiPath);
        }

        ApiPath savedApiSpec = apiPathRepository.saveAndFlush(apiPath);
        return apiSpecMapper.apiPathToApiPathDto(savedApiSpec);
    }

    private static boolean compareApiParameter(ApiParameter t1, Parameter t2) {
        return t1.getType().equals(t2.getSchema().getName()) && t1.getName().equals(t2.getName());
    }

    private static boolean compareApiSpecTag(ApiSpecMetadata t1, Tag t2) {
        return TAG_METADATA_NAME.equals(t1.getName()) && t1.getValue().equals(t2.getName());
    }

    private static boolean compareApiPathTag(ApiPathMetadata t1, String t2) {
        return TAG_METADATA_NAME.equals(t1.getName()) && t1.getValue().equals(t2);
    }
}
