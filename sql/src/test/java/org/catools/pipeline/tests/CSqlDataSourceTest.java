package org.catools.pipeline.tests;

import org.catools.common.collections.CList;
import org.catools.common.extensions.verify.CVerify;
import org.catools.sql.CSqlDataSource;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CSqlDataSourceTest extends CBaseTest {

  @Test
  public void testQueryObjectMapper() {
    String sql = "Select short_message " + VALID_FROM_COMMIT;
    SingleColumnRowMapper<String> rowMapper = new SingleColumnRowMapper<>();
    CVerify.Object.equals(CSqlDataSource.QueryObject.query(sql, PRIMARY), commit.getShortMessage());
    CVerify.Object.equals(CSqlDataSource.QueryObject.query(sql, rowMapper, PRIMARY), commit.getShortMessage());
    CVerify.Object.equals(CSqlDataSource.QueryObject.query(sql, rowMapper, PRIMARY, String::isBlank, 1, 1), commit.getShortMessage());
    CVerify.Object.isNull(CSqlDataSource.QueryObject.query(sql, new MapSqlParameterSource(), rowMapper, PRIMARY, i -> !i.isBlank(), 1, 1));
  }

  @Test
  public void testQueryObjectMapper_EmptyResult() {
    String invalidSql = "Select short_message " + INVALID_FROM_COMMIT;
    SingleColumnRowMapper<String> rowMapper = new SingleColumnRowMapper<>();
    CVerify.Object.isNull(CSqlDataSource.QueryObject.query(invalidSql, PRIMARY));
    CVerify.Object.isNull(CSqlDataSource.QueryObject.query(invalidSql, rowMapper, PRIMARY));
  }

  @Test
  public void testQueryListMapper() {
    String sql = "Select short_message " + FROM_ALL;
    SingleColumnRowMapper<String> rowMapper = new SingleColumnRowMapper<>();
    CVerify.Collection.isNotEmpty(CSqlDataSource.QueryList.query(sql, rowMapper, PRIMARY));
    CVerify.Collection.isNotEmpty(CSqlDataSource.QueryList.query(sql, rowMapper, PRIMARY, i -> i.isEmpty(), 1, 1));
    CVerify.Collection.isEmpty(CSqlDataSource.QueryList.query(sql, new MapSqlParameterSource(), rowMapper, PRIMARY, i -> !i.isEmpty(), 1, 1));
  }

  @Test
  public void testQueryListMapper_EmptyResult() {
    String invalidSql = "Select short_message " + INVALID_FROM_COMMIT;
    SingleColumnRowMapper<String> rowMapper = new SingleColumnRowMapper<>();
    CVerify.Collection.isEmpty(CSqlDataSource.QueryList.query(invalidSql, rowMapper, PRIMARY));
  }

  @Test
  public void testQueryListString() {
    String sql = "Select short_message " + FROM_ALL;
    CVerify.Collection.isNotEmpty(CSqlDataSource.QueryList.query(sql, String.class, PRIMARY));
    CVerify.Collection.isNotEmpty(CSqlDataSource.QueryList.query(sql, String.class, PRIMARY, ArrayList::isEmpty, 1, 1));
    CVerify.Collection.isEmpty(CSqlDataSource.QueryList.query(sql, new MapSqlParameterSource(), String.class, PRIMARY, i -> !i.isEmpty(), 1, 1));
  }

  @Test
  public void testQueryListString_EmptyResult() {
    String invalidSql = "Select short_message " + INVALID_FROM_COMMIT;
    CVerify.Collection.isEmpty(CSqlDataSource.QueryList.query(invalidSql, String.class, PRIMARY));
  }

  @Test
  public void testQueryListMap() {
    String sql = "Select short_message " + FROM_ALL;
    CVerify.Collection.isNotEmpty(CSqlDataSource.QueryList.query(sql, PRIMARY));
    CVerify.Collection.isNotEmpty(CSqlDataSource.QueryList.query(sql, PRIMARY, i -> i.isEmpty(), 1, 1));
    CVerify.Collection.isEmpty(CSqlDataSource.QueryList.query(sql, new MapSqlParameterSource(), PRIMARY, i -> !i.isEmpty(), 1, 1));
  }

  @Test
  public void testQueryListMap_EmptyResult() {
    String invalidSql = "Select short_message " + INVALID_FROM_COMMIT;
    CVerify.Collection.isEmpty(CSqlDataSource.QueryList.query(invalidSql, PRIMARY));
  }

  @Test
  public void testQueryMap() {
    String sql = "Select * " + VALID_FROM_COMMIT;
    CVerify.Map.isNotEmpty(CSqlDataSource.QueryMap.query(sql, PRIMARY));
    CVerify.Map.isNotEmpty(CSqlDataSource.QueryMap.query(sql, PRIMARY, i -> i.isEmpty(), 1, 1));
    CVerify.Map.isEmpty(CSqlDataSource.QueryMap.query(sql, new MapSqlParameterSource(), PRIMARY, i -> !i.isEmpty(), 1, 1));
  }

  @Test
  public void testQueryMap_EmptyResult() {
    String invalidSql = "Select * " + INVALID_FROM_COMMIT;
    CVerify.Map.isEmpty(CSqlDataSource.QueryMap.query(invalidSql, PRIMARY));
  }

  @Test
  public void testQueryString() {
    String sql = "Select short_message " + VALID_FROM_COMMIT;
    CVerify.String.equals(CSqlDataSource.QueryString.query(sql, PRIMARY), commit.getShortMessage());
    CVerify.String.equals(CSqlDataSource.QueryString.query(sql, PRIMARY, String::isBlank, 1, 1), commit.getShortMessage());
    CVerify.Object.isNull(CSqlDataSource.QueryString.query(sql, new MapSqlParameterSource(), PRIMARY, i -> !i.isBlank(), 1, 1));
  }

  @Test
  public void testQueryString_EmptyResult() {
    String invalidSql = "Select short_message " + INVALID_FROM_COMMIT;
    CVerify.Object.isNull(CSqlDataSource.QueryString.query(invalidSql, PRIMARY));
  }

  @Test
  public void testQueryDate() {
    String sql = "Select commit_time " + VALID_FROM_COMMIT;
    CVerify.Date.equals(CSqlDataSource.QueryDate.query(sql, PRIMARY), commit.getCommitTime());
    CVerify.Date.equals(CSqlDataSource.QueryDate.query(sql, PRIMARY, Objects::isNull, 1, 1), commit.getCommitTime());
    CVerify.Object.isNull(CSqlDataSource.QueryDate.query(sql, new MapSqlParameterSource(), PRIMARY, Objects::nonNull, 1, 1));
  }

  @Test
  public void testQueryDate_EmptyResult() {
    String invalidSql = "Select commit_time " + INVALID_FROM_COMMIT;
    CVerify.Object.isNull(CSqlDataSource.QueryDate.query(invalidSql, PRIMARY));
  }

  @Test
  public void testQueryLong() {
    CVerify.Long.greaterOrEqual(CSqlDataSource.QueryLong.query(SELECT_COUNT, PRIMARY), 70L);
    CVerify.Long.greaterOrEqual(CSqlDataSource.QueryLong.query(SELECT_COUNT, PRIMARY, i -> i < 70, 1, 1), 70L);
    CVerify.Object.isNull(CSqlDataSource.QueryLong.query(SELECT_COUNT, new MapSqlParameterSource(), PRIMARY, i -> i < 80, 1, 1));
  }

  @Test
  public void testQueryLong_EmptyResult() {
    String invalidSql = "Select author_id " + INVALID_FROM_COMMIT;
    CVerify.Object.isNull(CSqlDataSource.QueryLong.query(invalidSql, PRIMARY));
  }

  @Test
  public void testQueryBigDecimal() {
    CVerify.BigDecimal.greaterOrEqual(CSqlDataSource.QueryBigDecimal.query(SELECT_COUNT, PRIMARY), new BigDecimal("70"));
    CVerify.BigDecimal.greaterOrEqual(CSqlDataSource.QueryBigDecimal.query(SELECT_COUNT, PRIMARY, i -> i.intValue() < 70, 1, 1), new BigDecimal("70"));
    CVerify.Object.isNull(CSqlDataSource.QueryBigDecimal.query(SELECT_COUNT, new MapSqlParameterSource(), PRIMARY, i -> i.intValue() < 80, 1, 1));
  }

  @Test
  public void testQueryBigDecimal_EmptyResult() {
    String invalidSql = "Select author_id " + INVALID_FROM_COMMIT;
    CVerify.Object.isNull(CSqlDataSource.QueryBigDecimal.query(invalidSql, PRIMARY));
  }

  @Test
  public void testQueryDouble() {
    CVerify.Double.greaterOrEqual(CSqlDataSource.QueryDouble.query(SELECT_COUNT, PRIMARY), 70D);
    CVerify.Double.greaterOrEqual(CSqlDataSource.QueryDouble.query(SELECT_COUNT, PRIMARY, i -> i < 70, 1, 1), 70D);
    CVerify.Object.isNull(CSqlDataSource.QueryDouble.query(SELECT_COUNT, new MapSqlParameterSource(), PRIMARY, i -> i < 80, 1, 1));
  }

  @Test
  public void testQueryDouble_EmptyResult() {
    String invalidSql = "Select author_id " + INVALID_FROM_COMMIT;
    CVerify.Object.isNull(CSqlDataSource.QueryDouble.query(invalidSql, PRIMARY));
  }

  @Test
  public void testQueryInt() {
    CVerify.Int.greaterOrEqual(CSqlDataSource.QueryInt.query(SELECT_COUNT, PRIMARY), 70);
    CVerify.Int.greaterOrEqual(CSqlDataSource.QueryInt.query(SELECT_COUNT, PRIMARY, i -> i < 70, 1, 1), 70);
    CVerify.Object.isNull(CSqlDataSource.QueryInt.query(SELECT_COUNT, new MapSqlParameterSource(), PRIMARY, i -> i < 80, 1, 1));
  }

  @Test
  public void testQueryInt_EmptyResult() {
    String invalidSql = "Select author_id " + INVALID_FROM_COMMIT;
    CVerify.Object.isNull(CSqlDataSource.QueryInt.query(invalidSql, PRIMARY));
  }

  @Test
  public void testUpdateNoParam() {
    String sql1 = "update git.\"commit\" set full_message='1' where hash='d78457d822a84787cd23bcbd04a7b01ec4a6de6a'";
    String sql2 = "update git.\"commit\" set full_message='2' where hash='d78457d822a84787cd23bcbd04a7b01ec4a6de6a'";
    String sql3 = "update git.\"commit\" set full_message='3' where hash='d78457d822a84787cd23bcbd04a7b01ec4a6de6a'";
    CSqlDataSource.Batch.update(CList.of(sql1, sql2, sql3), PRIMARY);
  }

  @Test
  public void testUpdateWithParam() {
    String sql = "update git.\"commit\" set full_message=':message' where hash='d78457d822a84787cd23bcbd04a7b01ec4a6de6a'";
    List<MapSqlParameterSource> params = new ArrayList<>();
    params.add(new MapSqlParameterSource().addValue("message", "11"));
    params.add(new MapSqlParameterSource().addValue("message", "12"));
    params.add(new MapSqlParameterSource().addValue("message", "13"));
    params.add(new MapSqlParameterSource().addValue("message", "14"));
    CSqlDataSource.Batch.update(sql, params, PRIMARY);
  }

  @Test
  public void testDelete() {
    String sql1 = "Delete from git.file_change where id=2";
    CSqlDataSource.delete(sql1, PRIMARY);
  }

  @Test
  public void testInsert() {
    String sql1 = "INSERT INTO git.file_change\n" +
        "(id, deleted, inserted, new_path, \"path\", commit_id)\n" +
        "VALUES(20000, 10, 6, 'README.md', 'README.md', 1)";
    CSqlDataSource.insert(sql1, PRIMARY);
  }

  @Test
  public void testUpdate() {
    String sql1 = "Update git.file_change\n" +
        "set commit_id=2\n" +
        "where id =2";
    CSqlDataSource.update(sql1, PRIMARY);
  }
}
