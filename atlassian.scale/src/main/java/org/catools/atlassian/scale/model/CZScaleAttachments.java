package org.catools.atlassian.scale.model;

import org.catools.common.collections.CSet;

import java.util.stream.Stream;

public class CZScaleAttachments extends CSet<CZScaleAttachment> {
  public CZScaleAttachments() {
  }

  public CZScaleAttachments(CZScaleAttachment... c) {
    super(c);
  }

  public CZScaleAttachments(Stream<CZScaleAttachment> stream) {
    super(stream);
  }

  public CZScaleAttachments(Iterable<CZScaleAttachment> iterable) {
    super(iterable);
  }
}
