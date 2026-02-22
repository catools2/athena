package org.catools.athena.rest.feign.git.entity;

import java.util.HashSet;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MetadataPatternSet extends HashSet<MetadataPatternInfo> {}
