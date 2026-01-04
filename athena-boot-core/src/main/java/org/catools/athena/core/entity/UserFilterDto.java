package org.catools.athena.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for User entity filter parameters.
 * Provides type-safe filtering for User queries.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Filter parameters for User entity queries")
public class UserFilterDto {

  @Schema(description = "Filter by username property", example = "john")
  private String username;

  @Schema(description = "Filter by alias property", example = "doe")
  private String alias;

  /**
   * Convert UserFilterDto to filter string format for internal use
   * Format: "property:value,property2:value2"
   *
   * @return filter string
   */
  public String toFilterString() {
    StringBuilder sb = new StringBuilder();

    if (username != null && !username.trim().isEmpty()) {
      if (sb.length() > 0) sb.append(",");
      sb.append("username:").append(username.trim());
    }

    if (alias != null && !alias.trim().isEmpty()) {
      if (sb.length() > 0) sb.append(",");
      sb.append("alias:").append(alias.trim());
    }

    return sb.toString();
  }

  /**
   * Check if any filter is set
   *
   * @return true if at least one filter property is not null/empty
   */
  public boolean hasFilters() {
    return (username != null && !username.trim().isEmpty()) ||
           (alias != null && !alias.trim().isEmpty());
  }
}

