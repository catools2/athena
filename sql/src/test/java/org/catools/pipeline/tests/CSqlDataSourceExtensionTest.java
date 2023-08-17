package org.catools.pipeline.tests;

import org.catools.sql.CSqlDataSourceExtension;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.testng.annotations.Test;

import java.math.BigDecimal;

public class CSqlDataSourceExtensionTest extends CBaseTest {

  @Test
  public void testQueryObjectMapper() {
    String sql = "Select short_message " + VALID_FROM_COMMIT;
    SingleColumnRowMapper<String> rowMapper = new SingleColumnRowMapper<>();
    CSqlDataSourceExtension.Object.query(sql, PRIMARY, 1, 100).verifyEquals(commit.getShortMessage());
    CSqlDataSourceExtension.Object.query(sql, rowMapper, PRIMARY, 1, 100).verifyEquals(commit.getShortMessage());
  }

  @Test
  public void testQueryListMapper() {
    String sql = "Select short_message " + FROM_ALL;
    SingleColumnRowMapper<String> rowMapper = new SingleColumnRowMapper<>();
    CSqlDataSourceExtension.List.query(sql, rowMapper, PRIMARY, 1, 100).verifyIsNotEmpty();
  }

  @Test
  public void testQueryListString() {
    String sql = "Select short_message " + FROM_ALL;
    CSqlDataSourceExtension.List.query(sql, String.class, PRIMARY, 1, 100).verifyIsNotEmpty();
  }

  @Test
  public void testQueryListMap() {
    String sql = "Select * " + VALID_FROM_COMMIT;
    CSqlDataSourceExtension.Map.query(sql, PRIMARY, 1, 100).verifyIsNotEmpty();
  }

  @Test
  public void testQueryString() {
    String sql = "Select short_message " + VALID_FROM_COMMIT;
    CSqlDataSourceExtension.String.query(sql, PRIMARY, 1, 100).verifyEquals(commit.getShortMessage());
  }

  @Test
  public void testQueryString_EmptyResult() {
    String invalidSql = "Select short_message " + INVALID_FROM_COMMIT;
    CSqlDataSourceExtension.String.query(invalidSql, PRIMARY, 1, 100).verifyIsNull();
  }

  @Test
  public void testQueryDate() {
    String sql = "Select commit_time " + VALID_FROM_COMMIT;
    CSqlDataSourceExtension.Date.query(sql, PRIMARY, 1, 100).verifyEqualsByFormat(commit.getCommitTime(), DATE_FORMAT);
  }

  @Test
  public void testQueryDate_EmptyResult() {
    String invalidSql = "Select commit_time " + INVALID_FROM_COMMIT;
    CSqlDataSourceExtension.Date.query(invalidSql, PRIMARY, 1, 100).verifyIsNull();
  }

  @Test
  public void testQueryLong() {
    CSqlDataSourceExtension.Long.query(SELECT_COUNT, PRIMARY, 1, 100).verifyGreaterOrEqual(70L);
  }

  @Test
  public void testQueryBigDecimal() {
    CSqlDataSourceExtension.BigDecimal.query(SELECT_COUNT, PRIMARY, 1, 100).verifyGreaterOrEqual(new BigDecimal("70"));
  }

  @Test
  public void testQueryDouble() {
    CSqlDataSourceExtension.Double.query(SELECT_COUNT, PRIMARY, 1, 100).verifyGreaterOrEqual(70D);
  }

  @Test
  public void testQueryInt() {
    CSqlDataSourceExtension.Int.query(SELECT_COUNT, PRIMARY, 1, 100).verifyGreaterOrEqual(70);
  }
}
