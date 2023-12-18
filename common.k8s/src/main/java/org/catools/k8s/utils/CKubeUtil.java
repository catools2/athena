package org.catools.k8s.utils;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.common.collections.CHashMap;
import org.catools.k8s.exception.CKubeOperationException;
import org.catools.k8s.model.*;

import java.sql.Date;
import java.util.List;

@Slf4j
@UtilityClass
public class CKubeUtil {


  /**
   * Get the namespace Pods
   *
   * @param namespace object name and auth scope, such as for teams and projects (required)
   * @return the namespace Pods
   */
  public static CKubePods getNamespacePods(CoreV1Api api, String namespace) {
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
      kubePod.setName(pod.getMetadata().getName());
      kubePod.setUid(pod.getMetadata().getUid());

      if (pod.getMetadata().getCreationTimestamp() != null)
        kubePod.setCreatedAt(Date.from(pod.getMetadata().getCreationTimestamp().toInstant()));

      if (pod.getMetadata().getDeletionTimestamp() != null)
        kubePod.setDeletedAt(Date.from(pod.getMetadata().getDeletionTimestamp().toInstant()));

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
      kubeContainer.setStartedAt(Date.from(containerState.getRunning().getStartedAt().toInstant()));

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
    podStatus.setPhase(status.getPhase());

    return podStatus;
  }
}
