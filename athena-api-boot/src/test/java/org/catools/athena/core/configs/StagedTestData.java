package org.catools.athena.core.configs;

import org.catools.athena.core.common.entity.AppVersion;
import org.catools.athena.core.common.entity.Environment;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.entity.User;
import org.catools.athena.core.common.entity.UserAlias;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class StagedTestData {

  private static final Map<Integer, Project> PROJECTS = new HashMap<>() {{
    put(1, new Project().setId(1L).setCode("ADM").setName("Administration"));
    put(2, new Project().setId(2L).setCode("STG").setName("Storage"));
    put(3, new Project().setId(3L).setCode("ATO").setName("Automation"));
    put(4, new Project().setId(4L).setCode("FIN").setName("Finance"));
    put(5, new Project().setId(5L).setCode("ORG").setName("Organization"));
  }};

  private static final Map<Integer, User> USERS = new HashMap<>() {{
    put(1, new User(1L, "AKeshmiri", new UserAlias().setAlias("ak")));
    put(2, new User(2L, "JDoe", new UserAlias().setAlias("jd"), new UserAlias().setAlias("jone doe")));
    put(3, new User(3L, "JSmith", new UserAlias().setAlias("ak")));
    put(4, new User(4L, "AHolmes"));
    put(5, new User(5L, "BRyan"));
  }};

  private static final Map<Integer, Environment> ENVIRONMENTS = new HashMap<>() {{
    put(1, getEnvironment(1L, "DEV", "Development", PROJECTS.get(1)));
    put(2, getEnvironment(2L, "SMK", "Development Smoke", PROJECTS.get(1)));
    put(3, getEnvironment(3L, "QA", "QA", PROJECTS.get(1)));
    put(4, getEnvironment(4L, "STG", "Stagin", PROJECTS.get(1)));
    put(5, getEnvironment(5L, "PRD", "Production", PROJECTS.get(1)));

    put(6, getEnvironment(6L, "DEV", "Development", PROJECTS.get(2)));
    put(7, getEnvironment(7L, "SMK", "Development Smoke", PROJECTS.get(2)));
    put(8, getEnvironment(8L, "QA", "QA", PROJECTS.get(2)));
    put(9, getEnvironment(9L, "STG", "Stagin", PROJECTS.get(2)));
    put(10, getEnvironment(10L, "PRD", "Production", PROJECTS.get(2)));

    put(11, getEnvironment(11L, "DEV", "Development", PROJECTS.get(3)));
    put(12, getEnvironment(12L, "SMK", "Development Smoke", PROJECTS.get(3)));
    put(13, getEnvironment(13L, "QA", "QA", PROJECTS.get(3)));
    put(14, getEnvironment(14L, "STG", "Stagin", PROJECTS.get(3)));
    put(15, getEnvironment(15L, "PRD", "Production", PROJECTS.get(3)));

    put(16, getEnvironment(16L, "DEV", "Development", PROJECTS.get(4)));
    put(17, getEnvironment(17L, "SMK", "Development Smoke", PROJECTS.get(4)));
    put(18, getEnvironment(18L, "QA", "QA", PROJECTS.get(4)));
    put(19, getEnvironment(19L, "STG", "Stagin", PROJECTS.get(4)));
    put(20, getEnvironment(20L, "PRD", "Production", PROJECTS.get(4)));

    put(21, getEnvironment(21L, "DEV", "Development", PROJECTS.get(5)));
    put(22, getEnvironment(22L, "SMK", "Development Smoke", PROJECTS.get(5)));
    put(23, getEnvironment(23L, "QA", "QA", PROJECTS.get(5)));
    put(24, getEnvironment(24L, "STG", "Stagin", PROJECTS.get(5)));
    put(25, getEnvironment(25L, "PRD", "Production", PROJECTS.get(5)));
  }};

  private static final Map<Integer, AppVersion> VERSIONS = new HashMap<>() {{
    put(1, getVersion(1L, "1.0", PROJECTS.get(1)));
    put(2, getVersion(2L, "1.1", PROJECTS.get(1)));
    put(3, getVersion(3L, "2-15", PROJECTS.get(1)));
    put(4, getVersion(4L, "2021-02", PROJECTS.get(1)));
    put(5, getVersion(5L, "20210203", PROJECTS.get(1)));
    put(6, getVersion(6L, "v2021-03", PROJECTS.get(2)));
    put(7, getVersion(7L, "v2021", PROJECTS.get(2)));
    put(8, getVersion(8L, "2021", PROJECTS.get(2)));
    put(9, getVersion(9L, "123", PROJECTS.get(2)));
    put(10, getVersion(10L, "1.1", PROJECTS.get(2)));
    put(11, getVersion(11L, "1.2", PROJECTS.get(3)));
    put(12, getVersion(12L, "1.3", PROJECTS.get(3)));
    put(13, getVersion(13L, "1.4", PROJECTS.get(3)));
    put(14, getVersion(14L, "1.5", PROJECTS.get(3)));
    put(15, getVersion(15L, "1.6", PROJECTS.get(3)));
    put(16, getVersion(16L, "1.1", PROJECTS.get(4)));
    put(17, getVersion(17L, "1.2", PROJECTS.get(4)));
    put(18, getVersion(18L, "1.3", PROJECTS.get(4)));
    put(19, getVersion(19L, "1.4", PROJECTS.get(4)));
    put(20, getVersion(20L, "1.5", PROJECTS.get(4)));
    put(21, getVersion(21L, "1.1", PROJECTS.get(5)));
    put(22, getVersion(22L, "1.2", PROJECTS.get(5)));
    put(23, getVersion(23L, "1.3", PROJECTS.get(5)));
    put(24, getVersion(24L, "1.4", PROJECTS.get(5)));
    put(25, getVersion(25L, "1.5", PROJECTS.get(5)));
  }};

  public static Project getRandomProject() {
    return PROJECTS.values().stream().findAny().orElse(null);
  }

  public static Project getProject() {
    return getProject(1);
  }

  public static Project getProject(int id) {
    return PROJECTS.get(id);
  }

  public static User getRandomUser() {
    return USERS.values().stream().findAny().orElse(null);
  }

  public static User getUser() {
    return getUser(1);
  }

  public static User getUser(int id) {
    return USERS.get(id);
  }

  public static Environment getRandomEnvironment() {
    return ENVIRONMENTS.values().stream().findAny().orElse(null);
  }

  public static Environment getEnvironment() {
    return getEnvironment(1);
  }

  public static Environment getEnvironment(int id) {
    return ENVIRONMENTS.get(id);
  }

  public static AppVersion getRandomVersion() {
    return VERSIONS.values().stream().findAny().orElse(null);
  }

  public static AppVersion getVersion() {
    return getVersion(1);
  }

  public static AppVersion getVersion(int id) {
    return VERSIONS.get(id);
  }

  @NotNull
  private static Environment getEnvironment(long id, String code, String name, Project project) {
    return new Environment().setId(id).setCode(code + project.getId()).setName(name + " environment for " + project.getName()).setProject(project);
  }

  @NotNull
  private static AppVersion getVersion(long id, String code, Project project) {
    return new AppVersion().setId(id).setCode(code + project.getId()).setName(code + " version for " + project.getName()).setProject(project);
  }
}