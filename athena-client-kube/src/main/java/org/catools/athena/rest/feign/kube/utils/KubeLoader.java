package org.catools.athena.rest.feign.kube.utils;

import io.kubernetes.client.openapi.apis.CoreV1Api;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.model.kube.ContainerDto;
import org.catools.athena.model.kube.PodDto;
import org.catools.athena.rest.feign.core.cache.CoreCache;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;
import org.catools.athena.rest.feign.kube.clients.KubeClient;
import org.catools.athena.rest.feign.kube.configs.KubeConfigBuilder;

import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.catools.athena.rest.feign.common.utils.ThreadUtils.executeInParallel;

@Slf4j
@UtilityClass
public class KubeLoader {
  /**
   * Transfer KubePods from common.k8s package and load them to DB
   */
  public static void loadNamespaces(List<String> namespaces, int totalParallelProcessors, long timeoutInMinutes) {
    CoreCache.readProject(CoreConfigs.getProject());
    Instant lastSync = Instant.now();
    for (String namespace : namespaces) {
      CoreV1Api kubeApiClient = KubeConfigBuilder.getKubeApiClient();
      Set<PodDto> pods = KubeUtil.getNamespacePods(kubeApiClient, namespace);
      KubeLoader.loadPods(pods, lastSync, totalParallelProcessors, timeoutInMinutes);
    }
  }

  /**
   * Transfer KubePods from common.k8s package and load them to DB
   */
  public static void loadPods(Set<PodDto> pods, Instant lastSync, int totalParallelProcessors, long timeoutInMinutes) {
    setLastSyncValue(pods, lastSync);
    log.info("Loading {} pods", pods.size());
    AtomicInteger counter = new AtomicInteger();
    Iterator<PodDto> podsToLoad = pods.iterator();
    executeInParallel(totalParallelProcessors, timeoutInMinutes, () -> {
      while (true) {
        PodDto pod = null;
        synchronized (podsToLoad) {
          if (!podsToLoad.hasNext()) {
            return true;
          }
          pod = podsToLoad.next();
        }
        log.info("Process pod {}/{} {} with {} containers", counter.getAndIncrement(), pods.size(), pod.getName(), pod.getContainers().size());
        KubeClient.savePod(pod);
      }
    });
    log.info("{} pods loaded.", pods.size());
  }

  private static void setLastSyncValue(Set<PodDto> pods, Instant lastSync) {
    for (PodDto pod : pods) {
      pod.setLastSync(lastSync);
      for (ContainerDto container : pod.getContainers()) {
        container.setLastSync(lastSync);
      }
    }
  }
}
