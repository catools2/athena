package org.catools.athena.kube.common.mapper;

import org.catools.athena.core.common.mapper.CoreMapperService;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.kube.common.model.Container;
import org.catools.athena.kube.common.model.ContainerMetadata;
import org.catools.athena.kube.common.model.Pod;
import org.catools.athena.kube.common.model.PodAnnotation;
import org.catools.athena.kube.common.model.PodLabel;
import org.catools.athena.kube.common.model.PodMetadata;
import org.catools.athena.kube.common.model.PodSelector;
import org.catools.athena.kube.common.model.PodStatus;
import org.catools.athena.kube.model.ContainerDto;
import org.catools.athena.kube.model.PodDto;
import org.catools.athena.kube.model.PodStatusDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(uses = {CoreMapperService.class, KubeMapperService.class})
public interface KubeMapper {
  Pod podDtoToPod(PodDto pod);

  @Mapping(source = "project.code", target = "project")
  PodDto podToPodDto(Pod pod);

  Container containerDtoToContainer(ContainerDto container);

  ContainerDto containerToContainerDto(Container container);

  PodStatusDto podStatusToPodStatusDto(PodStatus status);

  PodStatus podStatusDtoToPodStatus(PodStatusDto status);

  MetadataDto containerMetadataToMetadataDto(ContainerMetadata metadata);

  ContainerMetadata metadataDtoToContainerMetadata(MetadataDto metadata);

  MetadataDto podMetadataToMetadataDto(PodMetadata metadata);

  PodMetadata metadataDtoToPodMetadata(MetadataDto metadata);

  MetadataDto podAnnotationToMetadataDto(PodAnnotation annotation);

  PodAnnotation metadataDtoToPodAnnotation(MetadataDto metadata);

  MetadataDto podLabelToMetadataDto(PodLabel label);

  PodLabel metadataDtoToPodLabel(MetadataDto metadata);

  MetadataDto podSelectorToMetadataDto(PodSelector selector);

  PodSelector metadataDtoToPodSelector(MetadataDto metadata);
}
