package org.catools.athena.rest.feign.git.configs;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import org.catools.athena.rest.feign.common.configs.ConfigUtils;
import org.catools.athena.rest.feign.common.utils.JsonUtils;
import org.catools.athena.rest.feign.git.entity.MetadataPatternSet;
import org.catools.athena.rest.feign.git.entity.RepoInfoSet;

@UtilityClass
public class GitConfigs {

  static {
    reload();
  }

  @Setter
  @Getter
  private static String username;

  @Setter
  @Getter
  private static String password;

  @Setter
  @Getter
  private static String name;

  @Setter
  @Getter
  private static String url;

  @Getter
  private static RepoInfoSet repoInfoSet;

  @Getter
  private static MetadataPatternSet metadataPatternSet;

  @Setter
  @Getter
  private static String localPath;

  public static void setRepoInfo(String input) {
    repoInfoSet = JsonUtils.readValue(input, RepoInfoSet.class);
  }

  public static void setMetadataPatternSet(String input) {
    metadataPatternSet = JsonUtils.readValue(input, MetadataPatternSet.class);
  }

  public static void reload() {
    username = ConfigUtils.getString("athena.git.username");
    password = ConfigUtils.getString("athena.git.password");
    name = ConfigUtils.getString("athena.git.repo.name");
    url = ConfigUtils.getString("athena.git.repo.url");
    repoInfoSet = ConfigUtils.asModel("athena.git.repo.set", RepoInfoSet.class);
    metadataPatternSet = ConfigUtils.asModel("athena.git.metadata.pattern.set", MetadataPatternSet.class);
    localPath = ConfigUtils.getString("athena.git.local_path");
  }
}
