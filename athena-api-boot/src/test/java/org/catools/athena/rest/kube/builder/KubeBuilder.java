package org.catools.athena.rest.kube.builder;

import lombok.experimental.UtilityClass;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.core.model.NameValuePair;
import org.catools.athena.kube.model.ContainerDto;
import org.catools.athena.kube.model.ContainerStateDto;
import org.catools.athena.kube.model.PodDto;
import org.catools.athena.kube.model.PodStatusDto;
import org.catools.athena.rest.core.builder.CoreBuilder;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.kube.model.*;
import org.instancio.Instancio;

import java.util.Set;
import java.util.stream.Collectors;

import static org.instancio.Select.field;

@UtilityClass
public class KubeBuilder {
    public static Pod buildPod(Project project) {
        return Instancio.of(Pod.class)
                .ignore(field(Pod::getId))
                .generate(field(Pod::getUid), gen -> gen.text().uuid())
                .generate(field(Pod::getName), gen -> gen.string().length(1, 500))
                .generate(field(Pod::getNamespace), gen -> gen.string().length(1, 100))
                .generate(field(Pod::getHostname), gen -> gen.string().length(1, 200))
                .generate(field(Pod::getNodeName), gen -> gen.string().length(1, 200))
                .set(field(Pod::getStatus), buildPodStatus())
                .set(field(Pod::getProject), project)
                .set(field(Pod::getMetadata), buildPodMetadata())
                .set(field(Pod::getAnnotations), buildPodAnnotation())
                .set(field(Pod::getLabels), buildPodLabels())
                .set(field(Pod::getSelectors), buildPodSelectors())
                .create();
    }

    public static PodDto buildPodDto(Pod pod) {
        return new PodDto()
                .setId(pod.getId())
                .setUid(pod.getUid())
                .setName(pod.getName())
                .setNamespace(pod.getNamespace())
                .setHostname(pod.getHostname())
                .setNodeName(pod.getNodeName())
                .setDeletedAt(pod.getDeletedAt())
                .setCreatedAt(pod.getCreatedAt())
                .setStatus(buildPodStatusDto(pod.getStatus()))
                .setProject(pod.getProject().getCode())
                .setMetadata(pod.getMetadata().stream().map(KubeBuilder::buildMetadataDto).collect(Collectors.toSet()))
                .setAnnotations(pod.getAnnotations().stream().map(KubeBuilder::buildMetadataDto).collect(Collectors.toSet()))
                .setLabels(pod.getLabels().stream().map(KubeBuilder::buildMetadataDto).collect(Collectors.toSet()))
                .setSelectors(pod.getSelectors().stream().map(KubeBuilder::buildMetadataDto).collect(Collectors.toSet()));
    }

    public static Container buildContainer(Pod pod) {
        return Instancio.of(Container.class)
                .ignore(field(Container::getId))
                .generate(field(Container::getType), gen -> gen.string().length(1, 100))
                .generate(field(Container::getName), gen -> gen.string().length(1, 300))
                .generate(field(Container::getImage), gen -> gen.string().length(1, 1000))
                .generate(field(Container::getImageId), gen -> gen.string().length(1, 300))
                .set(field(Container::getPod), pod)
                .set(field(Container::getMetadata), buildContainerMetadata())
                .create();
    }

    public static ContainerDto buildContainerDto(String podName) {
        return Instancio.of(ContainerDto.class)
                .ignore(field(ContainerDto::getId))
                .generate(field(ContainerDto::getType), gen -> gen.string().length(1, 100))
                .generate(field(ContainerDto::getName), gen -> gen.string().length(1, 300))
                .generate(field(ContainerDto::getImage), gen -> gen.string().length(1, 1000))
                .generate(field(ContainerDto::getImageId), gen -> gen.string().length(1, 300))
                .set(field(ContainerDto::getPod), podName)
                .set(field(ContainerDto::getMetadata), CoreBuilder.buildMetadataDto())
                .create();
    }

    public static ContainerDto buildContainerDto(Container container, Set<ContainerStateDto> states) {
        return new ContainerDto()
                .setId(container.getId())
                .setPod(container.getPod().getName())
                .setType(container.getType())
                .setName(container.getName())
                .setImage(container.getImage())
                .setImageId(container.getImageId())
                .setReady(container.getReady())
                .setStarted(container.getStarted())
                .setRestartCount(container.getRestartCount())
                .setStartedAt(container.getStartedAt())
                .setStates(states)
                .setMetadata(container.getMetadata().stream().map(KubeBuilder::buildMetadataDto).collect(Collectors.toSet()));
    }

    public static MetadataDto buildMetadataDto(NameValuePair nvp) {
        return new MetadataDto()
                .setId(nvp.getId())
                .setName(nvp.getName())
                .setValue(nvp.getValue());
    }

    public static PodStatus buildPodStatus() {
        return Instancio.of(PodStatus.class)
                .ignore(field(PodStatus::getId))
                .generate(field(PodStatus::getName), gen -> gen.string().length(1, 200).nullable())
                .generate(field(PodStatus::getPhase), gen -> gen.string().length(1, 200).nullable())
                .generate(field(PodStatus::getMessage), gen -> gen.string().length(1, 1000).nullable())
                .generate(field(PodStatus::getReason), gen -> gen.string().length(1, 1000).nullable())
                .create();
    }

    public static PodStatusDto buildPodStatusDto(PodStatus podStatus) {
        return new PodStatusDto()
                .setId(podStatus.getId())
                .setName(podStatus.getName())
                .setMessage(podStatus.getMessage())
                .setPhase(podStatus.getPhase())
                .setReason(podStatus.getReason());
    }

    public static Set<PodSelector> buildPodSelectors() {
        return Instancio.of(PodSelector.class)
                .ignore(field(PodSelector::getId))
                .generate(field(PodSelector::getValue), gen -> gen.string().length(1, 1000))
                .generate(field(PodSelector::getName), gen -> gen.string().length(1, 300))
                .stream()
                .limit(10)
                .collect(Collectors.toSet());
    }

    public static Set<PodLabel> buildPodLabels() {
        return Instancio.of(PodLabel.class)
                .ignore(field(PodLabel::getId))
                .generate(field(PodLabel::getValue), gen -> gen.string().length(1, 1000))
                .generate(field(PodLabel::getName), gen -> gen.string().length(1, 300))
                .stream()
                .limit(10)
                .collect(Collectors.toSet());
    }

    public static Set<PodAnnotation> buildPodAnnotation() {
        return Instancio.of(PodAnnotation.class)
                .ignore(field(PodAnnotation::getId))
                .generate(field(PodAnnotation::getValue), gen -> gen.string().length(1, 1000))
                .generate(field(PodAnnotation::getName), gen -> gen.string().length(1, 300))
                .stream()
                .limit(10)
                .collect(Collectors.toSet());
    }

    public static Set<PodMetadata> buildPodMetadata() {
        return Instancio.of(PodMetadata.class)
                .ignore(field(PodMetadata::getId))
                .generate(field(PodMetadata::getValue), gen -> gen.string().length(1, 1000))
                .generate(field(PodMetadata::getName), gen -> gen.string().length(1, 300))
                .stream()
                .limit(10)
                .collect(Collectors.toSet());
    }

    public static Set<ContainerMetadata> buildContainerMetadata() {
        return Instancio.of(ContainerMetadata.class)
                .ignore(field(ContainerMetadata::getId))
                .generate(field(ContainerMetadata::getValue), gen -> gen.string().length(1, 1000))
                .generate(field(ContainerMetadata::getName), gen -> gen.string().length(1, 300))
                .stream()
                .limit(10)
                .collect(Collectors.toSet());
    }

    public static Set<ContainerStateDto> buildContainerStateDto() {
        return Instancio.of(ContainerStateDto.class)
                .ignore(field(ContainerStateDto::getId))
                .generate(field(ContainerStateDto::getType), gen -> gen.string().length(1, 100))
                .generate(field(ContainerStateDto::getMessage), gen -> gen.string().length(1, 1000))
                .generate(field(ContainerStateDto::getValue), gen -> gen.string().length(1, 1000))
                .stream()
                .limit(10)
                .collect(Collectors.toSet());
    }

    public static ContainerState buildContainerState() {
        return Instancio.of(ContainerState.class)
                .ignore(field(ContainerState::getId))
                .generate(field(ContainerState::getType), gen -> gen.string().length(1, 100))
                .generate(field(ContainerState::getMessage), gen -> gen.string().length(1, 1000))
                .generate(field(ContainerState::getValue), gen -> gen.string().length(1, 1000))
                .create();
    }

    public static ContainerState buildContainerState(ContainerStateDto state, Container container) {
        return new ContainerState()
                .setId(state.getId())
                .setMessage(state.getMessage())
                .setSyncTime(state.getSyncTime())
                .setValue(state.getValue())
                .setType(state.getType())
                .setContainer(container);
    }
}
