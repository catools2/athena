package org.catools.athena.rest.feign.apispec.helpers;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.model.apispec.ApiSpecDto;
import org.catools.athena.rest.feign.apispec.client.ApiSpecClient;
import org.catools.athena.rest.feign.apispec.configs.OpenApiConfigs;
import org.catools.athena.rest.feign.apispec.utils.ApiSpecUtils;
import org.catools.athena.rest.feign.core.cache.CoreCache;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import static org.catools.athena.rest.feign.common.utils.FeignUtils.getClient;

@Slf4j
@UtilityClass
@SuppressWarnings("unused")
public class ApiSpecLoader {
  public static final ApiSpecClient API_SPEC_CLIENT = getClient(ApiSpecClient.class, CoreConfigs.getAthenaHost());

  public static void saveOpenApi() throws IOException {
    saveOpenApi(OpenApiConfigs.getSpecNames(), OpenApiConfigs.getSpecUrls());
  }

  public static void saveOpenApi(final List<String> specNames, final List<String> urls) throws IOException {
    Objects.requireNonNull(specNames);
    Objects.requireNonNull(urls);

    if (specNames.size() != urls.size()) {
      throw new RuntimeException(String.format("Number of spec names and urls does not match! names: %s, urls: %s", specNames.size(), urls.size()));
    }

    for (int i = 0; i < specNames.size(); i++) {
      saveOpenApi(specNames.get(i), URI.create(urls.get(i)).toURL());
    }
  }

  public static void saveOpenApi(final String specName, URL url) throws IOException {
    OpenAPI openAPI = Json.mapper().readValue(url, OpenAPI.class);
    saveApiSpec(openAPI, specName);
  }

  public static void saveApiSpec(OpenAPI openAPI, String specName) {
    CoreCache.readProject(CoreConfigs.getProject());
    ApiSpecDto apiSpec = ApiSpecUtils.getApiSpec(openAPI, specName, CoreConfigs.getProjectCode());
    API_SPEC_CLIENT.saveOrUpdate(apiSpec);
  }
}
