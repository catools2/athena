package org.catools.etl.k8s.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.common.utils.CStringUtil;
import org.catools.etl.k8s.cache.CEtlKubeCacheManager;
import org.catools.etl.k8s.dao.CEtlKubeBaseDao;
import org.catools.etl.k8s.model.*;
import org.catools.k8s.model.CKubeContainer;
import org.catools.k8s.model.CKubeContainerStateInfo;
import org.catools.k8s.model.CKubePod;
import org.catools.k8s.model.CKubePods;

import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Slf4j
@UtilityClass
public class CEtlKubeLoader {
  /**
   * Transfer KubePods from common.k8s package and load them to DB
   *
   * @param projectName
   * @param pods
   */
  public static void loadPods(String projectName, CKubePods pods) {
    for (CKubePod pod : pods) {
      CEtlKubePod kubePod = translatePod(projectName, pod);
      CEtlKubeBaseDao.merge(kubePod);
    }
  }

  /**
   * Transfer KubePod to ETLKubePod and return the result
   *
   * @param projectName
   * @param pod
   * @return
   */
  public static CEtlKubePod translatePod(String projectName, CKubePod pod) {
    Objects.requireNonNull(pod.getStatus(), "Pod status is required");
    Objects.requireNonNull(pod.getSpec(), "Pod spec is required");

    CEtlKubeProject project = CEtlKubeCacheManager.getProject(projectName);
    CEtlKubePodStatus status = CEtlKubeCacheManager.getStatus(pod.getStatus().getStatus(), pod.getStatus().getType(), pod.getStatus().getPhase(), pod.getStatus().getMessage(), pod.getStatus().getReason());
    CEtlKubePod kubePod = new CEtlKubePod(pod.getSpec().getHostname(), pod.getSpec().getNodeName(), project, status);

    pod.getAnnotations().forEach((k, v) -> kubePod.addMetaData(new CEtlKubePodMetaData("ANNOTATION", k, v)));
    pod.getLabels().forEach((k, v) -> kubePod.addMetaData(new CEtlKubePodMetaData("LABEL", k, v)));
    pod.getMetadata().forEach((k, v) -> kubePod.addMetaData(new CEtlKubePodMetaData("METADATA", k, v)));

    pod.getContainers().forEach(c -> kubePod.addContainer(translateContainer("eternal", c)));
    pod.getEphemeralContainers().forEach(c -> kubePod.addContainer(translateContainer("ephemeral", c)));
    pod.getInitContainers().forEach(c -> kubePod.addContainer(translateContainer("init", c)));

    return kubePod;
  }

  private static CEtlKubeContainer translateContainer(String type, CKubeContainer c) {
    Date startedAt = c.getStartedAt() == null ? null : Date.from(c.getStartedAt().atStartOfDay(ZoneId.systemDefault()).toInstant());
    CEtlKubeContainer kubeContainer = new CEtlKubeContainer(
        type,
        c.getName(),
        c.getImage(),
        c.getImageId(),
        c.getReady(),
        c.getStarted(),
        c.getRestartCount(),
        startedAt);

    CKubeContainerStateInfo terminatedState = c.getTerminatedState();
    if (terminatedState != null) {
      if (CStringUtil.isNotBlank(terminatedState.getMessage()))
        kubeContainer.addMetaData(new CEtlKubeContainerMetaData("terminated_state_message", terminatedState.getMessage()));

      if (CStringUtil.isNotBlank(terminatedState.getReason()))
        kubeContainer.addMetaData(new CEtlKubeContainerMetaData("terminated_state_reason", terminatedState.getReason()));
    }

    CKubeContainerStateInfo waitingState = c.getWaitingState();
    if (waitingState != null) {
      if (CStringUtil.isNotBlank(waitingState.getMessage()))
        kubeContainer.addMetaData(new CEtlKubeContainerMetaData("waiting_state_message", waitingState.getMessage()));

      if (CStringUtil.isNotBlank(waitingState.getReason()))
        kubeContainer.addMetaData(new CEtlKubeContainerMetaData("waiting_state_reason", waitingState.getReason()));
    }

    return kubeContainer;
  }

}
