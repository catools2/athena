package org.catools.etl.k8s.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.common.concurrent.CParallelRunner;
import org.catools.common.date.CDate;
import org.catools.common.utils.CStringUtil;
import org.catools.etl.k8s.cache.CEtlKubeCacheManager;
import org.catools.etl.k8s.dao.CEtlKubeBaseDao;
import org.catools.etl.k8s.dao.CEtlKubePodDao;
import org.catools.etl.k8s.model.CEtlKubeContainer;
import org.catools.etl.k8s.model.CEtlKubePod;
import org.catools.etl.k8s.model.CEtlKubePodStatus;
import org.catools.etl.k8s.model.CEtlKubeProject;
import org.catools.k8s.model.CKubeContainer;
import org.catools.k8s.model.CKubeContainerStateInfo;
import org.catools.k8s.model.CKubePod;
import org.catools.k8s.model.CKubePods;

import java.util.Iterator;
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
  public static void loadPods(String projectName, CKubePods pods, int totalParallelProcessors) {
    Iterator<CKubePod> podsToLoad = pods.iterator();
    CDate loadTime = CDate.now();
    CParallelRunner<Boolean> runner = new CParallelRunner<>("Load Pods", totalParallelProcessors, () -> {
      while (true) {
        CKubePod pod = null;
        synchronized (podsToLoad) {
          if (!podsToLoad.hasNext()) break;
          pod = podsToLoad.next();
        }
        if (pod != null) {
          CEtlKubePodDao.deletePodByNameIfExists(pod.getName());

          CEtlKubePod kubePod = translatePod(projectName, pod, loadTime);
          CEtlKubeBaseDao.merge(kubePod);
        }
      }
      return true;
    });

    try {
      runner.invokeAll();
    } catch (Throwable e) {
      throw new RuntimeException("Failed to load pods", e);
    }
  }

  /**
   * Transfer KubePod to ETLKubePod and return the result
   *
   * @param projectName
   * @param pod
   * @return
   */
  public static CEtlKubePod translatePod(String projectName, CKubePod pod, CDate loadTime) {
    Objects.requireNonNull(pod.getStatus(), "Pod status is required");
    Objects.requireNonNull(pod.getSpec(), "Pod spec is required");

    CEtlKubeProject project = CEtlKubeCacheManager.getProject(projectName);
    CEtlKubePodStatus status = CEtlKubeCacheManager.getStatus(pod.getStatus().getStatus(), pod.getStatus().getPhase(), pod.getStatus().getMessage(), pod.getStatus().getReason());
    CEtlKubePod kubePod = new CEtlKubePod(pod.getName(), pod.getUid(), pod.getSpec().getHostname(), pod.getSpec().getNodeName(), pod.getCreatedAt(), pod.getDeletedAt(), project, status);

    pod.getAnnotations().forEach((k, v) -> kubePod.addMetaData(CEtlKubeCacheManager.getPodMetadata("ANNOTATION", k, v)));
    pod.getLabels().forEach((k, v) -> kubePod.addMetaData(CEtlKubeCacheManager.getPodMetadata("LABEL", k, v)));
    pod.getMetadata().forEach((k, v) -> kubePod.addMetaData(CEtlKubeCacheManager.getPodMetadata("METADATA", k, v)));

    pod.getContainers().forEach(c -> kubePod.addContainer(translateContainer("eternal", c, loadTime)));
    pod.getEphemeralContainers().forEach(c -> kubePod.addContainer(translateContainer("ephemeral", c, loadTime)));
    pod.getInitContainers().forEach(c -> kubePod.addContainer(translateContainer("init", c, loadTime)));

    return kubePod;
  }

  private static CEtlKubeContainer translateContainer(String type, CKubeContainer c, CDate loadTime) {
    CEtlKubeContainer kubeContainer = new CEtlKubeContainer(
        type,
        c.getName(),
        c.getImage(),
        c.getImageId(),
        c.getReady(),
        c.getStarted(),
        c.getRestartCount(),
        c.getStartedAt(),
        loadTime);

    CKubeContainerStateInfo terminatedState = c.getTerminatedState();
    if (terminatedState != null) {
      if (CStringUtil.isNotBlank(terminatedState.getMessage()))
        kubeContainer.addMetaData(CEtlKubeCacheManager.getContainerMetadata("terminated_message", terminatedState.getMessage()));

      if (CStringUtil.isNotBlank(terminatedState.getReason()))
        kubeContainer.addMetaData(CEtlKubeCacheManager.getContainerMetadata("terminated_reason", terminatedState.getReason()));
    }

    CKubeContainerStateInfo waitingState = c.getWaitingState();
    if (waitingState != null) {
      if (CStringUtil.isNotBlank(waitingState.getMessage()))
        kubeContainer.addMetaData(CEtlKubeCacheManager.getContainerMetadata("waiting_message", waitingState.getMessage()));

      if (CStringUtil.isNotBlank(waitingState.getReason()))
        kubeContainer.addMetaData(CEtlKubeCacheManager.getContainerMetadata("waiting_reason", waitingState.getReason()));
    }

    return kubeContainer;
  }

}
