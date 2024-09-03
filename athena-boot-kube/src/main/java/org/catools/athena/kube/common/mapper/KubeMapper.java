package org.catools.athena.kube.common.mapper;

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


@Mapper(uses = {KubeMapperService.class})
public interface KubeMapper {
  @Mapping(source = "project", target = "projectId")
  @Mapping(target = "containers", expression = "java(pod.getContainers() == null ? null : pod.getContainers().stream().map(c -> containerDtoToContainer(pod1, c)).collect(java.util.stream.Collectors.toSet()))")
  Pod podDtoToPod(PodDto pod);

  @Mapping(source = "projectId", target = "project")
  PodDto podToPodDto(Pod pod);

  @Mapping(source = "pod", target = "pod")
  @Mapping(source = "container.id", target = "id")
  @Mapping(source = "container.name", target = "name")
  @Mapping(source = "container.lastSync", target = "lastSync")
  @Mapping(source = "container.metadata", target = "metadata")
  Container containerDtoToContainer(Pod pod, ContainerDto container);

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
