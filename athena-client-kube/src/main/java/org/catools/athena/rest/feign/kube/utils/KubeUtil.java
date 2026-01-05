package org.catools.athena.rest.feign.kube.utils;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1ContainerState;
import io.kubernetes.client.openapi.models.V1ContainerStateTerminated;
import io.kubernetes.client.openapi.models.V1ContainerStateWaiting;
import io.kubernetes.client.openapi.models.V1ContainerStatus;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.openapi.models.V1PodSpec;
import io.kubernetes.client.openapi.models.V1PodStatus;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.model.core.MetadataDto;
import org.catools.athena.model.kube.ContainerDto;
import org.catools.athena.model.kube.PodDto;
import org.catools.athena.model.kube.PodStatusDto;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.catools.athena.rest.feign.kube.exception.KubeOperationException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@UtilityClass
public class KubeUtil {


  /**
   * Get the namespace Pods
   *
   * @param namespace object name and auth scope, such as for teams and projects (required)
   * @return the namespace Pods
   */
  public static Set<PodDto> getNamespacePods(CoreV1Api api, String namespace) {
    return getNamespacePods(api, namespace, null, null, null, null, null, null, null, null, null, null);
  }

  /**
   * Get the namespace Pods
   *
   * @param namespace            object name and auth scope, such as for teams and projects (required)
   * @param pretty               If &#39;true&#39;, then the output is pretty printed. (optional)
   * @param allowWatchBookmarks  allowWatchBookmarks requests watch events with type \&quot;BOOKMARK\&quot;. Servers that do not implement bookmarks may ignore this flag and bookmarks are sent at the server&#39;s discretion. Clients should not assume bookmarks are returned at any specific interval, nor may they assume the server will send any BOOKMARK event during a session. If this is not a watch, this field is ignored. (optional)
   * @param _continue            The continue option should be set when retrieving more results from the server. Since this value is server defined, clients may only use the continue value from a previous query result with identical query parameters (except for the value of continue) and the server may reject a continue value it does not recognize. If the specified continue value is no longer valid whether due to expiration (generally five to fifteen minutes) or a configuration change on the server, the server will respond with a 410 ResourceExpired error together with a continue token. If the client needs a consistent list, it must restart their list without the continue field. Otherwise, the client may send another list request with the token received with the 410 error, the server will respond with a list starting from the next key, but from the latest snapshot, which is inconsistent from the previous list results - objects that are created, modified, or deleted after the first list request will be included in the response, as long as their keys are after the \&quot;next key\&quot;.  This field is not supported when watch is true. Clients may start a watch from the last resourceVersion value returned by the server and not miss any modifications. (optional)
   * @param fieldSelector        A selector to restrict the list of returned objects by their fields. Defaults to everything. (optional)
   * @param labelSelector        A selector to restrict the list of returned objects by their labels. Defaults to everything. (optional)
   * @param limit                limit is a maximum number of responses to return for a list call. If more items exist, the server will set the &#x60;continue&#x60; field on the list metadata to a value that can be used with the same initial query to retrieve the next set of results. Setting a limit may return fewer than the requested amount of items (up to zero items) in the event all requested objects are filtered out and clients should only use the presence of the continue field to determine whether more results are available. Servers may choose not to support the limit argument and will return all of the available results. If limit is specified and the continue field is empty, clients may assume that no more results are available. This field is not supported if watch is true.  The server guarantees that the objects returned when using continue will be identical to issuing a single list call without a limit - that is, no objects created, modified, or deleted after the first request is issued will be included in any subsequent continued requests. This is sometimes referred to as a consistent snapshot, and ensures that a client that is using limit to receive smaller chunks of a very large result can ensure they see all possible objects. If objects are updated during a chunked list the version of the object that was present at the time the first list result was calculated is returned. (optional)
   * @param resourceVersion      resourceVersion sets a constraint on what resource versions a request may be served from. See https://kubernetes.io/docs/reference/using-api/api-concepts/#resource-versions for details.  Defaults to unset (optional)
   * @param resourceVersionMatch resourceVersionMatch determines how resourceVersion is applied to list calls. It is highly recommended that resourceVersionMatch be set for list calls where resourceVersion is set See https://kubernetes.io/docs/reference/using-api/api-concepts/#resource-versions for details.  Defaults to unset (optional)
   * @param timeoutSeconds       Timeout for the list/watch call. This limits the duration of the call, regardless of any activity or inactivity. (optional)
   * @param watch                Watch for changes to the described resources and return them as a stream of add, update, and remove notifications. Specify resourceVersion. (optional)
   * @return the namespace Pods
   * @throws ApiException If fail to serialize the request body object
   */
  public static Set<PodDto> getNamespacePods(CoreV1Api api,
                                             String namespace,
                                             String pretty,
                                             Boolean allowWatchBookmarks,
                                             String _continue,
                                             String fieldSelector,
                                             String labelSelector,
                                             Integer limit,
                                             String resourceVersion,
                                             String resourceVersionMatch,
                                             Integer timeoutSeconds,
                                             Boolean watch) {
    Set<PodDto> pods = new HashSet<>();

    try {
      V1PodList podList = api.listNamespacedPod(namespace,
          pretty,
          allowWatchBookmarks,
          _continue,
          fieldSelector,
          labelSelector,
          limit,
          resourceVersion,
          resourceVersionMatch,
          null,
          timeoutSeconds,
          watch);
      podList.getItems().forEach(pod -> pods.add(readPod(namespace, pod)));
      return pods;
    } catch (ApiException e) {
      throw new KubeOperationException(String.format(
          "Failed to read namespace pods. namespace : '%s', fieldSelector: '%s', labelSelector: '%s', resourceVersion: '%s', resourceVersionMatch: '%s'",
          namespace,
          fieldSelector,
          labelSelector,
          resourceVersion,
          resourceVersionMatch), e);
    }
  }

  private static PodDto readPod(String namespace, V1Pod kubePod) {
    PodDto pod = new PodDto();
    pod.setProject(CoreConfigs.getProjectCode());
    pod.setNamespace(namespace);

    if (kubePod == null) {
      return pod;
    }

    if (kubePod.getMetadata() != null) {
      pod.setName(kubePod.getMetadata().getName());
      pod.setUid(kubePod.getMetadata().getUid());

      if (kubePod.getMetadata().getCreationTimestamp() != null) {
        pod.setCreatedAt(kubePod.getMetadata().getCreationTimestamp().toInstant());
      }

      if (kubePod.getMetadata().getDeletionTimestamp() != null) {
        pod.setDeletedAt(kubePod.getMetadata().getDeletionTimestamp().toInstant());
      }

      if (kubePod.getMetadata().getAnnotations() != null) {
        kubePod.getMetadata().getAnnotations().forEach((k, v) -> pod.getAnnotations().add(new MetadataDto(k, v)));
      }

      if (kubePod.getMetadata().getLabels() != null) {
        kubePod.getMetadata().getLabels().forEach((k, v) -> pod.getLabels().add(new MetadataDto(k, v)));
      }
    }

    pod.setStatus(readPodStatus(kubePod));

    readPodSpec(pod, kubePod);
    readPodContainers(pod, kubePod);

    return pod;
  }

  private static void readPodContainers(PodDto kubePod, V1Pod pod) {
    V1PodStatus status = pod.getStatus();

    kubePod.getContainers().clear();

    if (status == null) {
      return;
    }

    if (status.getContainerStatuses() != null) {
      kubePod.getContainers().addAll(readContainers("eternal", status.getContainerStatuses()));
    }

    if (status.getEphemeralContainerStatuses() != null) {
      kubePod.getContainers().addAll(readContainers("ephemeral", status.getEphemeralContainerStatuses()));
    }

    if (status.getInitContainerStatuses() != null) {
      kubePod.getContainers().addAll(readContainers("init", status.getInitContainerStatuses()));
    }
  }

  private static void readPodSpec(PodDto pod, V1Pod kubePod) {
    if (kubePod == null) {
      return;
    }

    V1PodSpec spec = kubePod.getSpec();
    if (spec != null) {
      if (spec.getNodeSelector() != null) {
        spec.getNodeSelector().forEach((k, v) -> pod.getSelectors().add(new MetadataDto(k, v)));
      }

      pod.setHostname(spec.getHostname());
      pod.setNodeName(spec.getNodeName());
    }
  }

  private static Set<ContainerDto> readContainers(final String type, List<V1ContainerStatus> kubeContainers) {
    Set<ContainerDto> containers = new HashSet<>();

    for (V1ContainerStatus status : kubeContainers) {
      ContainerDto container = new ContainerDto();
      container.setType(type);

      if (status.getState() != null) {
        readContainerStates(container, status.getState());
      }

      container.setImage(status.getImage());
      container.setImageId(status.getImageID());
      container.setName(status.getName());
      container.setReady(status.getReady());
      container.setRestartCount(status.getRestartCount());
      container.setStarted(status.getStarted());

      containers.add(container);
    }

    return containers;
  }

  private static void readContainerStates(ContainerDto container, V1ContainerState containerState) {
    if (containerState.getRunning() != null && containerState.getRunning().getStartedAt() != null) {
      container.setStartedAt(containerState.getRunning().getStartedAt().toInstant());
    }

    if (containerState.getTerminated() != null) {
      readTerminatedStateInfo(container, containerState);
    }

    if (containerState.getWaiting() != null) {
      readWaitingStateInfo(container, containerState);
    }
  }

  private static void readWaitingStateInfo(ContainerDto container, V1ContainerState kubeContainer) {
    V1ContainerStateWaiting stateWaiting = kubeContainer.getWaiting();
    if (stateWaiting == null) {
      return;
    }

    if (StringUtils.isNotBlank(stateWaiting.getMessage())) {
      container.getMetadata().add(new MetadataDto("waiting_message", stateWaiting.getMessage()));
    }

    if (StringUtils.isNotBlank(stateWaiting.getReason())) {
      container.getMetadata().add(new MetadataDto("waiting_reason", stateWaiting.getReason()));
    }
  }

  private static void readTerminatedStateInfo(ContainerDto container, V1ContainerState kubeContainer) {
    V1ContainerStateTerminated stateTerminated = kubeContainer.getTerminated();
    if (stateTerminated == null) {
      return;
    }
    if (StringUtils.isNotBlank(stateTerminated.getMessage())) {
      container.getMetadata().add(new MetadataDto("terminated_message", stateTerminated.getMessage()));
    }

    if (StringUtils.isNotBlank(stateTerminated.getReason())) {
      container.getMetadata().add(new MetadataDto("terminated_reason", stateTerminated.getReason()));
    }
  }

  private static PodStatusDto readPodStatus(V1Pod pod) {
    PodStatusDto podStatus = new PodStatusDto();

    V1PodStatus status = pod.getStatus();
    if (status == null) {
      return podStatus;
    }

    podStatus.setMessage(status.getMessage());
    podStatus.setReason(status.getReason());
    podStatus.setPhase(status.getPhase());

    return podStatus;
  }
}
