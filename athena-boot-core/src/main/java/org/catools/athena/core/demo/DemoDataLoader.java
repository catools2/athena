package org.catools.athena.core.demo;

import lombok.extern.slf4j.Slf4j;
import org.catools.athena.core.common.entity.Environment;
import org.catools.athena.core.common.entity.Project;
import org.catools.athena.core.common.entity.User;
import org.catools.athena.core.common.entity.UserAlias;
import org.catools.athena.core.common.repository.EnvironmentRepository;
import org.catools.athena.core.common.repository.ProjectRepository;
import org.catools.athena.core.common.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Loads demo/test data into the database on application startup.
 * Only runs when 'athena.demo-data.enabled=true' is set.
 *
 * <p>Data structure based on: orchestration/demo-db/demo.sql</p>
 */
@Slf4j
@Component
@ConditionalOnProperty(
    prefix = "athena.demo-data",
    name = "enabled",
    havingValue = "true"
)
public class DemoDataLoader implements ApplicationRunner {

  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;
  private final EnvironmentRepository environmentRepository;

  public DemoDataLoader(
      ProjectRepository projectRepository,
      UserRepository userRepository,
      EnvironmentRepository environmentRepository) {
    this.projectRepository = projectRepository;
    this.userRepository = userRepository;
    this.environmentRepository = environmentRepository;
  }

  @Override
  @Transactional
  public void run(ApplicationArguments args) {
    log.info("🎭 Loading demo data into database...");

    try {
      loadProjects();
      loadUsers();
      loadEnvironments();

      log.info("Demo data loaded successfully!");
    } catch (Exception e) {
      log.error("Failed to load demo data", e);
      throw new RuntimeException("Failed to load demo data", e);
    }
  }

  private void loadProjects() {
    createProjectIfNotExists("ADM", "Administration");
    createProjectIfNotExists("STG", "Storage");
    createProjectIfNotExists("ATO", "Automation");
    createProjectIfNotExists("FIN", "Finance");
    createProjectIfNotExists("ORG", "Organization");
  }

  private void createProjectIfNotExists(String code, String name) {
    if (projectRepository.findByCode(code).isEmpty()) {
      Project project = new Project()
          .setCode(code)
          .setName(name);
      projectRepository.save(project);
      log.debug("Created project: {} - {}", code, name);
    }
  }

  private void loadUsers() {
    createUserWithAliasIfNotExists("AKeshmiri", "ak");
    createUserWithAliasIfNotExists("JDoe", "jd", "jone doe");
    createUserWithAliasIfNotExists("AHolmes");
    createUserWithAliasIfNotExists("BRyan");
  }

  private void createUserWithAliasIfNotExists(String username, String... aliases) {
    if (userRepository.findByUsername(username).isEmpty()) {
      User user = new User()
          .setUsername(username);

      // Add aliases if provided
      for (String alias : aliases) {
        UserAlias userAlias = new UserAlias()
            .setAlias(alias)
            .setUser(user);
        user.getAliases().add(userAlias);
      }

      userRepository.save(user);
      log.debug("Created user: {} with {} alias(es)", username, aliases.length);
    }
  }

  private void loadEnvironments() {
    // Load environments for each project
    loadEnvironmentsForProject("ADM", 1);
    loadEnvironmentsForProject("STG", 2);
    loadEnvironmentsForProject("ATO", 3);
    loadEnvironmentsForProject("FIN", 4);
    loadEnvironmentsForProject("ORG", 5);
  }

  private void loadEnvironmentsForProject(String projectCode, int suffix) {
    Project project = projectRepository.findByCode(projectCode)
        .orElseThrow(() -> new IllegalStateException("Project not found: " + projectCode));

    String projectName = project.getName();

    createEnvironmentIfNotExists(project, "DEV" + suffix, "Development environment for " + projectName);
    createEnvironmentIfNotExists(project, "SMK" + suffix, "Development Smoke environment for " + projectName);
    createEnvironmentIfNotExists(project, "QA" + suffix, "QA environment for " + projectName);
    createEnvironmentIfNotExists(project, "STG" + suffix, "Staging environment for " + projectName);
    createEnvironmentIfNotExists(project, "PRD" + suffix, "Production environment for " + projectName);
  }

  private void createEnvironmentIfNotExists(Project project, String code, String name) {
    if (environmentRepository.findByCode(code).isEmpty()) {
      Environment env = new Environment()
          .setCode(code)
          .setName(name)
          .setProject(project);
      environmentRepository.save(env);
      log.debug("Created environment: {} for project {}", code, project.getCode());
    }
  }
}

