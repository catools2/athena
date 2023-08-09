package org.catools.k8s.utils;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.common.collections.CHashMap;
import org.catools.k8s.configs.CKubeConfigBuilder;
import org.catools.k8s.exception.CKubeOperationException;
import org.catools.k8s.model.*;

import java.util.List;

@Slf4j
@UtilityClass
public class CKubeUtil {

  /**
   * Get pod information for specific namespace using default client
   *
   * @param namespace
   * @return
   */
  public static CKubePods getNamespacePods(String namespace) {
    CoreV1Api api = CKubeConfigBuilder.getKubeApiClient();
    return getNamespacePods(api, namespace);
  }

  /**
   * Get pod information for specific namespace using provided client
   *
   * @param api       the api client to use for interaction
   * @param namespace
   * @return
   */
  public static CKubePods getNamespacePods(CoreV1Api api, String namespace) {
    return getNamespacePods(api, namespace, null, null, null, null, null, null, null, null, null, null);
  }

  public static CKubePods getNamespacePods(CoreV1Api api, String namespace, String pretty, Boolean allowWatchBookmarks, String _continue, String fieldSelector, String labelSelector, Integer limit, String resourceVersion, String resourceVersionMatch, Integer timeoutSeconds, Boolean watch) {
    CKubePods pods = new CKubePods();

    try {
      V1PodList podList = api.listNamespacedPod(namespace, pretty, allowWatchBookmarks, _continue, fieldSelector, labelSelector, limit, resourceVersion, resourceVersionMatch, timeoutSeconds, watch);
      podList.getItems().forEach(pod -> pods.add(readPod(pod)));
      return pods;
    } catch (ApiException e) {
      throw new CKubeOperationException(String.format("Failed to read namespace pods. namespace : '%s', fieldSelector: '%s', labelSelector: '%s', resourceVersion: '%s', resourceVersionMatch: '%s'", namespace, fieldSelector, labelSelector, resourceVersion, resourceVersionMatch), e);
    }
  }

  private static CKubePod readPod(V1Pod pod) {
    CKubePod kubePod = new CKubePod();

    if (pod == null) return kubePod;

    if (pod.getMetadata() != null) {
      if (pod.getMetadata().getAnnotations() != null)
        kubePod.setAnnotations(new CHashMap<>(pod.getMetadata().getAnnotations()));

      if (pod.getMetadata().getLabels() != null)
        kubePod.setLabels(new CHashMap<>(pod.getMetadata().getLabels()));
    }

    kubePod.setSpec(readPodSpec(pod));
    kubePod.setStatus(readPodStatus(pod));

    readPodContainers(kubePod, pod);

    return kubePod;
  }

  private static void readPodContainers(CKubePod kubePod, V1Pod pod) {
    V1PodStatus status = pod.getStatus();
    if (status == null) return;

    if (status.getContainerStatuses() != null)
      kubePod.setContainers(readContainers(status.getContainerStatuses()));

    if (status.getEphemeralContainerStatuses() != null)
      kubePod.setEphemeralContainers(readContainers(status.getEphemeralContainerStatuses()));

    if (status.getInitContainerStatuses() != null)
      kubePod.setInitContainers(readContainers(status.getInitContainerStatuses()));
  }

  private static CKubePodSpec readPodSpec(V1Pod pod) {
    CKubePodSpec podSpec = new CKubePodSpec();

    if (pod == null) return podSpec;

    V1PodSpec spec = pod.getSpec();
    if (spec != null) {
      if (spec.getNodeSelector() != null)
        podSpec.setNodeSelector(new CHashMap<>(spec.getNodeSelector()));

      podSpec.setHostname(spec.getHostname());
      podSpec.setNodeName(spec.getNodeName());
    }

    return podSpec;
  }

  private static CKubeContainers readContainers(List<V1ContainerStatus> containers) {
    CKubeContainers kubeContainers = new CKubeContainers();
    for (V1ContainerStatus status : containers) {
      CKubeContainer kubeContainer = new CKubeContainer();

      if (status.getState() != null)
        readContainerStates(kubeContainer, status.getState());

      kubeContainer.setImage(status.getImage());
      kubeContainer.setImageId(status.getImageID());
      kubeContainer.setName(status.getName());
      kubeContainer.setReady(status.getReady());
      kubeContainer.setRestartCount(status.getRestartCount());
      kubeContainer.setStarted(status.getStarted());

      kubeContainers.add(kubeContainer);
    }
    return kubeContainers;
  }

  private static void readContainerStates(CKubeContainer kubeContainer, V1ContainerState containerState) {
    if (containerState.getRunning() != null && containerState.getRunning().getStartedAt() != null)
      kubeContainer.setStartedAt(containerState.getRunning().getStartedAt().toLocalDate());

    if (containerState.getTerminated() != null)
      kubeContainer.setTerminatedState(readTerminatedStateInfo(containerState));

    if (containerState.getWaiting() != null)
      kubeContainer.setWaitingState(readWaitingStateInfo(containerState));
  }

  private static CKubeContainerStateInfo readWaitingStateInfo(V1ContainerState container) {
    CKubeContainerStateInfo stateInfo = new CKubeContainerStateInfo();
    V1ContainerStateWaiting stateWaiting = container.getWaiting();
    if (stateWaiting == null) return stateInfo;
    stateInfo.setMessage(stateWaiting.getMessage());
    stateInfo.setReason(stateWaiting.getReason());
    return stateInfo;
  }

  private static CKubeContainerStateInfo readTerminatedStateInfo(V1ContainerState container) {
    CKubeContainerStateInfo stateInfo = new CKubeContainerStateInfo();
    V1ContainerStateTerminated stateTerminated = container.getTerminated();
    if (stateTerminated == null) return stateInfo;
    stateInfo.setMessage(stateTerminated.getMessage());
    stateInfo.setReason(stateTerminated.getReason());
    return stateInfo;
  }

  private static CKubePodStatus readPodStatus(V1Pod pod) {
    CKubePodStatus podStatus = new CKubePodStatus();

    V1PodStatus status = pod.getStatus();
    if (status == null) return podStatus;

    podStatus.setMessage(status.getMessage());
    podStatus.setReason(status.getReason());
    podStatus.setType(status.getPodIP());
    podStatus.setPhase(status.getPhase());

    return podStatus;
  }
}
