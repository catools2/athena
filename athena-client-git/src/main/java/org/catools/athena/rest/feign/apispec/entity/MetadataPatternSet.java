package org.catools.athena.rest.feign.apispec.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;

@Data
@EqualsAndHashCode(callSuper = true)
public class MetadataPatternSet extends HashSet<MetadataPatternInfo> {

}
