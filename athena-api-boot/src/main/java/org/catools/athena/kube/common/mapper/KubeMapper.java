package org.catools.athena.kube.common.mapper;

import org.catools.athena.core.common.mapper.CoreMapperService;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.kube.common.model.*;
import org.catools.athena.kube.model.ContainerDto;
import org.catools.athena.kube.model.PodDto;
import org.catools.athena.kube.model.PodStatusDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(uses = {CoreMapperService.class, KubeMapperService.class})
public abstract class KubeMapper {
  public abstract Pod podDtoToPod(PodDto pod);

  @Mapping(source = "project.code", target = "project")
  public abstract PodDto podToPodDto(Pod pod);

  public abstract Container containerDtoToContainer(ContainerDto container);

  public abstract ContainerDto containerToContainerDto(Container container);

  public abstract PodStatusDto podStatusToPodStatusDto(PodStatus status);

  public abstract PodStatus podStatusDtoToPodStatus(PodStatusDto status);

  public abstract MetadataDto containerMetadataToMetadataDto(ContainerMetadata metadata);

  public abstract ContainerMetadata metadataDtoToContainerMetadata(MetadataDto metadata);

  public abstract MetadataDto podMetadataToMetadataDto(PodMetadata metadata);

  public abstract PodMetadata metadataDtoToPodMetadata(MetadataDto metadata);

  public abstract MetadataDto podAnnotationToMetadataDto(PodAnnotation annotation);

  public abstract PodAnnotation metadataDtoToPodAnnotation(MetadataDto metadata);

  public abstract MetadataDto podLabelToMetadataDto(PodLabel label);

  public abstract PodLabel metadataDtoToPodLabel(MetadataDto metadata);

  public abstract MetadataDto podSelectorToMetadataDto(PodSelector selector);

  public abstract PodSelector metadataDtoToPodSelector(MetadataDto metadata);
}
