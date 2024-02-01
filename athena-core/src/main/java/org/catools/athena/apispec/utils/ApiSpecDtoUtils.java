package org.catools.athena.apispec.utils;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.tags.Tag;
import lombok.experimental.UtilityClass;
import org.catools.athena.apispec.model.ApiPathDto;
import org.catools.athena.apispec.model.ApiSpecDto;
import org.catools.athena.core.model.MetadataDto;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@UtilityClass
@SuppressWarnings("unused")
public class ApiSpecDtoUtils {
  private static final String TAG_METADATA_NAME = "TAG";

  public static ApiSpecDto getApiSpec(OpenAPI openAPI, String specName, String projectCode) {
    ApiSpecDto apiSpecToSave = new ApiSpecDto();

    apiSpecToSave.setName(specName);
    apiSpecToSave.setProject(projectCode);
    apiSpecToSave.setVersion(openAPI.getSpecVersion().name());
    apiSpecToSave.setTitle(openAPI.getInfo().getTitle());
    apiSpecToSave.setFirstTimeSeen(Instant.now());
    apiSpecToSave.setLastSyncTime(Instant.now());

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
    }

    return apiSpecToSave;
  }

  public static Set<ApiPathDto> getApiPaths(OpenAPI openAPI, Long apiSpecId) {
    Set<ApiPathDto> savedApiPaths = new HashSet<>();
    openAPI.getPaths().forEach((url, pathItem) -> savedApiPaths.addAll(getApiPath(url, pathItem, apiSpecId)));
    return savedApiPaths;
  }

  public static Set<ApiPathDto> getApiPath(String url, PathItem pathItem, Long apiSpecId) {
    Set<ApiPathDto> savedApiPaths = new HashSet<>();

    if (pathItem.getGet() != null)
      savedApiPaths.add(getApiPath(url, "GET", pathItem.getGet(), apiSpecId));

    if (pathItem.getPut() != null)
      savedApiPaths.add(getApiPath(url, "PUT", pathItem.getPut(), apiSpecId));

    if (pathItem.getPost() != null)
      savedApiPaths.add(getApiPath(url, "POST", pathItem.getPost(), apiSpecId));

    if (pathItem.getDelete() != null)
      savedApiPaths.add(getApiPath(url, "DELETE", pathItem.getDelete(), apiSpecId));

    if (pathItem.getOptions() != null)
      savedApiPaths.add(getApiPath(url, "OPTIONS", pathItem.getOptions(), apiSpecId));

    if (pathItem.getHead() != null)
      savedApiPaths.add(getApiPath(url, "HEAD", pathItem.getHead(), apiSpecId));

    if (pathItem.getPatch() != null)
      savedApiPaths.add(getApiPath(url, "PATCH", pathItem.getPatch(), apiSpecId));

    if (pathItem.getTrace() != null)
      savedApiPaths.add(getApiPath(url, "TRACE", pathItem.getTrace(), apiSpecId));

    return savedApiPaths;
  }

  private static ApiPathDto getApiPath(String url, String method, Operation operation, Long apiSpecId) {
    ApiPathDto apiPath = new ApiPathDto();

    apiPath.setMethod(method);
    apiPath.setTitle(operation.getSummary());
    apiPath.setDescription(operation.getDescription());
    apiPath.setUrl(url);
    apiPath.setApiSpecId(apiSpecId);
    apiPath.setFirstTimeSeen(Instant.now());
    apiPath.setLastSyncTime(Instant.now());

    if (operation.getParameters() != null) {
      for (Parameter param : operation.getParameters()) {
        if (param.getName() != null && param.getSchema() != null)
          apiPath.addParameter(param.getName(), param.getSchema().getType());
      }
    }

    if (operation.getTags() != null) {
      for (String tag : operation.getTags()) {
        if (apiPath.getMetadata().stream().noneMatch(t1 -> compareApiPathTag(t1, tag)))
          apiPath.addMetadata(TAG_METADATA_NAME, tag);
      }
    }

    return apiPath;
  }

  private static boolean compareApiSpecTag(MetadataDto t1, Tag t2) {
    return TAG_METADATA_NAME.equals(t1.getName()) && t1.getValue().equals(t2.getName());
  }

  private static boolean compareApiPathTag(MetadataDto t1, String t2) {
    return TAG_METADATA_NAME.equals(t1.getName()) && t1.getValue().equals(t2);
  }
}
