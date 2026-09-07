package org.catools.athena.model.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageDto<T> implements Serializable {

  private List<T> content;

  private int number;

  private int size;

  private int numberOfElements;

  private long totalElements;

  private int totalPages;

  private boolean first;

  private boolean last;

  private boolean empty;
}