package org.catools.athena.rest.feign.git.helpers;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.model.git.CommitDto;
import org.catools.athena.model.git.GitRepositoryDto;
import org.catools.athena.rest.feign.common.utils.FeignUtils;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.catools.athena.rest.feign.git.client.CommitClient;
import org.catools.athena.rest.feign.git.client.RepositoryClient;

import static org.catools.athena.rest.feign.common.utils.FeignUtils.getEntityId;

@Slf4j
@UtilityClass
public class AthenaGitApi {
  private static final RepositoryClient REPOSITORY_CLIENT = FeignUtils.getClient(RepositoryClient.class, CoreConfigs.getAthenaHost());
  private static final CommitClient COMMIT_CLIENT = FeignUtils.getClient(CommitClient.class, CoreConfigs.getAthenaHost());

  public static GitRepositoryDto getRepository(final String name) {
    return REPOSITORY_CLIENT.search(name);
  }

  public static void persistRepository(GitRepositoryDto repository) {
    getEntityId(REPOSITORY_CLIENT.save(repository)).ifPresent(repository::setId);
  }

  public static void persistCommit(CommitDto c) {
    getEntityId(COMMIT_CLIENT.save(c)).ifPresent(c::setId);
  }
}
