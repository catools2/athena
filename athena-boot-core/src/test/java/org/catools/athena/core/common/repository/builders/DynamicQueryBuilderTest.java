package org.catools.athena.core.common.repository.builders;

import org.catools.athena.core.entity.EnvironmentFilterDto;
import org.catools.athena.core.entity.ProjectFilterDto;
import org.catools.athena.core.entity.UserFilterDto;
import org.catools.athena.core.entity.VersionFilterDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Unit tests for DynamicQueryBuilder classes.
 * Tests JPQL query generation based on FilterDto parameters.
 */
@DisplayName("DynamicQueryBuilder Tests")
class DynamicQueryBuilderTest {

  @Nested
  @DisplayName("UserDynamicQueryBuilder Tests")
  class UserDynamicQueryBuilderTest {

    @Test
    @DisplayName("Should generate base query when no filters provided")
    void shouldGenerateBaseQueryWhenNoFiltersProvided() {
      UserFilterDto filterDto = new UserFilterDto();
      UserDynamicQueryBuilder builder = new UserDynamicQueryBuilder(filterDto);

      String query = builder.buildQuery();

      assertThat(query, equalTo("SELECT u FROM User u"));
    }

    @Test
    @DisplayName("Should generate query with username filter")
    void shouldGenerateQueryWithUsernameFilter() {
      UserFilterDto filterDto = UserFilterDto.builder()
          .username("testUser")
          .build();
      UserDynamicQueryBuilder builder = new UserDynamicQueryBuilder(filterDto);

      String query = builder.buildQuery();

      assertThat(query, notNullValue());
      assertThat(query, containsString("SELECT u FROM User u WHERE"));
      assertThat(query, containsString("lower(u.username)"));
      assertThat(query, containsString("testUser"));
    }

    @Test
    @DisplayName("Should generate query with alias filter")
    void shouldGenerateQueryWithAliasFilter() {
      UserFilterDto filterDto = UserFilterDto.builder()
          .alias("testAlias")
          .build();
      UserDynamicQueryBuilder builder = new UserDynamicQueryBuilder(filterDto);

      String query = builder.buildQuery();

      assertThat(query, notNullValue());
      assertThat(query, containsString("SELECT u FROM User u WHERE"));
      assertThat(query, containsString("UserAlias"));
      assertThat(query, containsString("testAlias"));
    }

    @Test
    @DisplayName("Should generate query with both username and alias filters")
    void shouldGenerateQueryWithBothFilters() {
      UserFilterDto filterDto = UserFilterDto.builder()
          .username("testUser")
          .alias("testAlias")
          .build();
      UserDynamicQueryBuilder builder = new UserDynamicQueryBuilder(filterDto);

      String query = builder.buildQuery();

      assertThat(query, notNullValue());
      assertThat(query, containsString("SELECT u FROM User u WHERE"));
      assertThat(query, containsString("AND"));
      assertThat(query, containsString("lower(u.username)"));
      assertThat(query, containsString("UserAlias"));
    }

    @Test
    @DisplayName("Should escape single quotes in filter values")
    void shouldEscapeSingleQuotes() {
      UserFilterDto filterDto = UserFilterDto.builder()
          .username("test'User")
          .build();
      UserDynamicQueryBuilder builder = new UserDynamicQueryBuilder(filterDto);

      String query = builder.buildQuery();

      assertThat(query, notNullValue());
      assertThat(query, containsString("test''User"));
    }
  }

  @Nested
  @DisplayName("ProjectDynamicQueryBuilder Tests")
  class ProjectDynamicQueryBuilderTest {

    @Test
    @DisplayName("Should generate base query when no filters provided")
    void shouldGenerateBaseQueryWhenNoFiltersProvided() {
      ProjectFilterDto filterDto = new ProjectFilterDto();
      ProjectDynamicQueryBuilder builder = new ProjectDynamicQueryBuilder(filterDto);

      String query = builder.buildQuery();

      assertThat(query, equalTo("SELECT p FROM Project p"));
    }

    @Test
    @DisplayName("Should generate query with code filter")
    void shouldGenerateQueryWithCodeFilter() {
      ProjectFilterDto filterDto = ProjectFilterDto.builder()
          .code("P100")
          .build();
      ProjectDynamicQueryBuilder builder = new ProjectDynamicQueryBuilder(filterDto);

      String query = builder.buildQuery();

      assertThat(query, notNullValue());
      assertThat(query, containsString("SELECT p FROM Project p WHERE"));
      assertThat(query, containsString("lower(p.code)"));
      assertThat(query, containsString("P100"));
    }

    @Test
    @DisplayName("Should generate query with name filter")
    void shouldGenerateQueryWithNameFilter() {
      ProjectFilterDto filterDto = ProjectFilterDto.builder()
          .name("Production")
          .build();
      ProjectDynamicQueryBuilder builder = new ProjectDynamicQueryBuilder(filterDto);

      String query = builder.buildQuery();

      assertThat(query, notNullValue());
      assertThat(query, containsString("SELECT p FROM Project p WHERE"));
      assertThat(query, containsString("lower(p.name)"));
      assertThat(query, containsString("Production"));
    }

    @Test
    @DisplayName("Should generate query with both code and name filters")
    void shouldGenerateQueryWithBothFilters() {
      ProjectFilterDto filterDto = ProjectFilterDto.builder()
          .code("P100")
          .name("Production")
          .build();
      ProjectDynamicQueryBuilder builder = new ProjectDynamicQueryBuilder(filterDto);

      String query = builder.buildQuery();

      assertThat(query, notNullValue());
      assertThat(query, containsString("SELECT p FROM Project p WHERE"));
      assertThat(query, containsString("AND"));
      assertThat(query, containsString("lower(p.code)"));
      assertThat(query, containsString("lower(p.name)"));
    }
  }

  @Nested
  @DisplayName("EnvironmentDynamicQueryBuilder Tests")
  class EnvironmentDynamicQueryBuilderTest {

    @Test
    @DisplayName("Should generate base query when no filters provided")
    void shouldGenerateBaseQueryWhenNoFiltersProvided() {
      EnvironmentFilterDto filterDto = new EnvironmentFilterDto();
      EnvironmentDynamicQueryBuilder builder = new EnvironmentDynamicQueryBuilder(filterDto);

      String query = builder.buildQuery();

      assertThat(query, equalTo("SELECT e FROM Environment e"));
    }

    @Test
    @DisplayName("Should generate query with code filter")
    void shouldGenerateQueryWithCodeFilter() {
      EnvironmentFilterDto filterDto = EnvironmentFilterDto.builder()
          .code("dev")
          .build();
      EnvironmentDynamicQueryBuilder builder = new EnvironmentDynamicQueryBuilder(filterDto);

      String query = builder.buildQuery();

      assertThat(query, notNullValue());
      assertThat(query, containsString("SELECT e FROM Environment e WHERE"));
      assertThat(query, containsString("lower(e.code)"));
    }

    @Test
    @DisplayName("Should generate query with project filter and join")
    void shouldGenerateQueryWithProjectFilterAndJoin() {
      EnvironmentFilterDto filterDto = EnvironmentFilterDto.builder()
          .project("P100")
          .build();
      EnvironmentDynamicQueryBuilder builder = new EnvironmentDynamicQueryBuilder(filterDto);

      String query = builder.buildQuery();

      assertThat(query, notNullValue());
      assertThat(query, containsString("JOIN e.project p"));
      assertThat(query, containsString("lower(p.code)"));
    }

    @Test
    @DisplayName("Should generate query with all three filters")
    void shouldGenerateQueryWithAllThreeFilters() {
      EnvironmentFilterDto filterDto = EnvironmentFilterDto.builder()
          .code("dev")
          .name("Development")
          .project("P100")
          .build();
      EnvironmentDynamicQueryBuilder builder = new EnvironmentDynamicQueryBuilder(filterDto);

      String query = builder.buildQuery();

      assertThat(query, notNullValue());
      assertThat(query, containsString("JOIN e.project p"));
      assertThat(query, containsString("lower(e.code)"));
      assertThat(query, containsString("lower(e.name)"));
      assertThat(query, containsString("lower(p.code)"));
    }
  }

  @Nested
  @DisplayName("VersionDynamicQueryBuilder Tests")
  class VersionDynamicQueryBuilderTest {

    @Test
    @DisplayName("Should generate base query when no filters provided")
    void shouldGenerateBaseQueryWhenNoFiltersProvided() {
      VersionFilterDto filterDto = new VersionFilterDto();
      VersionDynamicQueryBuilder builder = new VersionDynamicQueryBuilder(filterDto);

      String query = builder.buildQuery();

      assertThat(query, equalTo("SELECT v FROM AppVersion v"));
    }

    @Test
    @DisplayName("Should generate query with code filter")
    void shouldGenerateQueryWithCodeFilter() {
      VersionFilterDto filterDto = VersionFilterDto.builder()
          .code("v1.0")
          .build();
      VersionDynamicQueryBuilder builder = new VersionDynamicQueryBuilder(filterDto);

      String query = builder.buildQuery();

      assertThat(query, notNullValue());
      assertThat(query, containsString("SELECT v FROM AppVersion v WHERE"));
      assertThat(query, containsString("lower(v.code)"));
    }

    @Test
    @DisplayName("Should generate query with project filter and join")
    void shouldGenerateQueryWithProjectFilterAndJoin() {
      VersionFilterDto filterDto = VersionFilterDto.builder()
          .project("P100")
          .build();
      VersionDynamicQueryBuilder builder = new VersionDynamicQueryBuilder(filterDto);

      String query = builder.buildQuery();

      assertThat(query, notNullValue());
      assertThat(query, containsString("JOIN v.project p"));
      assertThat(query, containsString("lower(p.code)"));
    }

    @Test
    @DisplayName("Should generate query with all three filters")
    void shouldGenerateQueryWithAllThreeFilters() {
      VersionFilterDto filterDto = VersionFilterDto.builder()
          .code("v1.0")
          .name("Release 1.0")
          .project("P100")
          .build();
      VersionDynamicQueryBuilder builder = new VersionDynamicQueryBuilder(filterDto);

      String query = builder.buildQuery();

      assertThat(query, notNullValue());
      assertThat(query, containsString("JOIN v.project p"));
      assertThat(query, containsString("lower(v.code)"));
      assertThat(query, containsString("lower(v.name)"));
      assertThat(query, containsString("lower(p.code)"));
    }
  }
}

