package org.catools.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * For numeric values, if the value is null then sql returns the public value. The value cannot be
 * used in validation so we need set if values to return null in these scenarios for primitive
 * types.
 */
public class CResultSet {
  /**
   * Get the field value and return Null if the value was ${@code null} after reading it.
   *
   * @param columnIndex the column number.
   * @return Null if the value was ${@code null} otherwise the value
   * @throws SQLException if the columnIndex is not valid; if a database access error occurs or this
   *                      method is called on a closed result set
   */
  public static <T extends ResultSet> Boolean getBooleanOrNull(T t, int columnIndex)
      throws SQLException {
    return orNull(t, t.getBoolean(columnIndex));
  }

  /**
   * Get the field value and return Null if the value was ${@code null} after reading it.
   *
   * @param columnIndex the column number.
   * @return Null if the value was ${@code null} otherwise the value
   * @throws SQLException if the columnIndex is not valid; if a database access error occurs or this
   *                      method is called on a closed result set
   */
  public static <T extends ResultSet> Byte getByteOrNull(T t, int columnIndex) throws SQLException {
    return orNull(t, t.getByte(columnIndex));
  }

  /**
   * Get the field value and return Null if the value was ${@code null} after reading it.
   *
   * @param columnIndex the column number.
   * @return Null if the value was ${@code null} otherwise the value
   * @throws SQLException if the columnIndex is not valid; if a database access error occurs or this
   *                      method is called on a closed result set
   */
  public static <T extends ResultSet> Short getShortOrNull(T t, int columnIndex)
      throws SQLException {
    return orNull(t, t.getShort(columnIndex));
  }

  /**
   * Get the field value and return Null if the value was ${@code null} after reading it.
   *
   * @param columnIndex the column number.
   * @return Null if the value was ${@code null} otherwise the value
   * @throws SQLException if the columnIndex is not valid; if a database access error occurs or this
   *                      method is called on a closed result set
   */
  public static <T extends ResultSet> Integer getIntOrNull(T t, int columnIndex)
      throws SQLException {
    return orNull(t, t.getInt(columnIndex));
  }

  /**
   * Get the field value and return Null if the value was ${@code null} after reading it.
   *
   * @param columnIndex the column number.
   * @return Null if the value was ${@code null} otherwise the value
   * @throws SQLException if the columnIndex is not valid; if a database access error occurs or this
   *                      method is called on a closed result set
   */
  public static <T extends ResultSet> Long getLongOrNull(T t, int columnIndex) throws SQLException {
    return orNull(t, t.getLong(columnIndex));
  }

  /**
   * Get the field value and return Null if the value was ${@code null} after reading it.
   *
   * @param columnIndex the column number.
   * @return Null if the value was ${@code null} otherwise the value
   * @throws SQLException if the columnIndex is not valid; if a database access error occurs or this
   *                      method is called on a closed result set
   */
  public static <T extends ResultSet> Float getFloatOrNull(T t, int columnIndex)
      throws SQLException {
    return orNull(t, t.getFloat(columnIndex));
  }

  /**
   * Get the field value and return Null if the value was ${@code null} after reading it.
   *
   * @param columnIndex the column number.
   * @return Null if the value was ${@code null} otherwise the value
   * @throws SQLException if the columnIndex is not valid; if a database access error occurs or this
   *                      method is called on a closed result set
   */
  public static <T extends ResultSet> Double getDoubleOrNull(T t, int columnIndex)
      throws SQLException {
    return orNull(t, t.getDouble(columnIndex));
  }

  /**
   * Get the field value and return Null if the value was ${@code null} after reading it.
   *
   * @param columnLabel the column name.
   * @return Null if the value was ${@code null} otherwise the value
   * @throws SQLException if the columnIndex is not valid; if a database access error occurs or this
   *                      method is called on a closed result set
   */
  public static <T extends ResultSet> Boolean getBooleanOrNull(T t, String columnLabel)
      throws SQLException {
    return orNull(t, t.getBoolean(columnLabel));
  }

  /**
   * Get the field value and return Null if the value was ${@code null} after reading it.
   *
   * @param columnLabel the column name.
   * @return Null if the value was ${@code null} otherwise the value
   * @throws SQLException if the columnIndex is not valid; if a database access error occurs or this
   *                      method is called on a closed result set
   */
  public static <T extends ResultSet> Byte getByteOrNull(T t, String columnLabel)
      throws SQLException {
    return orNull(t, t.getByte(columnLabel));
  }

  /**
   * Get the field value and return Null if the value was ${@code null} after reading it.
   *
   * @param columnLabel the column name.
   * @return Null if the value was ${@code null} otherwise the value
   * @throws SQLException if the columnIndex is not valid; if a database access error occurs or this
   *                      method is called on a closed result set
   */
  public static <T extends ResultSet> Short getShortOrNull(T t, String columnLabel)
      throws SQLException {
    return orNull(t, t.getShort(columnLabel));
  }

  /**
   * Get the field value and return Null if the value was ${@code null} after reading it.
   *
   * @param columnLabel the column name.
   * @return Null if the value was ${@code null} otherwise the value
   * @throws SQLException if the columnIndex is not valid; if a database access error occurs or this
   *                      method is called on a closed result set
   */
  public static <T extends ResultSet> Integer getIntOrNull(T t, String columnLabel)
      throws SQLException {
    return orNull(t, t.getInt(columnLabel));
  }

  /**
   * Get the field value and return Null if the value was ${@code null} after reading it.
   *
   * @param columnLabel the column name.
   * @return Null if the value was ${@code null} otherwise the value
   * @throws SQLException if the columnIndex is not valid; if a database access error occurs or this
   *                      method is called on a closed result set
   */
  public static <T extends ResultSet> Long getLongOrNull(T t, String columnLabel)
      throws SQLException {
    return orNull(t, t.getLong(columnLabel));
  }

  /**
   * Get the field value and return Null if the value was ${@code null} after reading it.
   *
   * @param columnLabel the column name.
   * @return Null if the value was ${@code null} otherwise the value
   * @throws SQLException if the columnIndex is not valid; if a database access error occurs or this
   *                      method is called on a closed result set
   */
  public static <T extends ResultSet> Float getFloatOrNull(T t, String columnLabel)
      throws SQLException {
    return orNull(t, t.getFloat(columnLabel));
  }

  /**
   * Get the field value and return Null if the value was ${@code null} after reading it.
   *
   * @param columnLabel the column name.
   * @return Null if the value was ${@code null} otherwise the value
   * @throws SQLException if the columnIndex is not valid; if a database access error occurs or this
   *                      method is called on a closed result set
   */
  public static <T extends ResultSet> Double getDoubleOrNull(T t, String columnLabel)
      throws SQLException {
    return orNull(t, t.getDouble(columnLabel));
  }

  private static <T extends ResultSet, R> R orNull(T t, R result) throws SQLException {
    return t.wasNull() ? null : result;
  }
}
