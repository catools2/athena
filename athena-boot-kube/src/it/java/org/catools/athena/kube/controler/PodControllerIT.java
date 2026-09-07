package org.catools.athena.kube.controler;

import feign.TypedResponse;
import org.catools.athena.common.feign.FeignUtils;
import org.catools.athena.kube.builder.KubeBuilder;
import org.catools.athena.kube.common.model.Pod;
import org.catools.athena.model.kube.PodDto;
import org.catools.athena.model.kube.PodInventoryDto;
import org.catools.athena.model.kube.PodStatusDto;
import org.catools.athena.model.kube.PodSummaryDto;
import org.catools.athena.model.kube.PodTrendPointDto;
import org.catools.athena.core.kube.PageResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PodControllerIT extends KubeControllerIT {

  private static PodDto dashboardPodOne;
  private static PodDto dashboardPodTwo;
  private static final String DASHBOARD_NAMESPACE_PREFIX = "runtime-dashboard-" + System.currentTimeMillis();

  @Test
  @Order(1)
  void saveShallSavePodIfDoesNotExist() {
    Pod pod = KubeBuilder.buildPod(projectDto);
    PodDto podDto = kubeMapper.podToPodDto(pod);

    TypedResponse<Void> response = podFeignClient.saveOrUpdate(podDto);
    assertThat(response.status(), equalTo(201));
    Long entityId = FeignUtils.getIdFromLocationHeader(response);
    assertThat(entityId, notNullValue());
  }

  @Test
  @Order(2)
  void saveShallUpdatePodIdPodWithTheSameNameAndNamespaceExists() {
    Pod pod = KubeBuilder.buildPod(projectDto);
    pod.getAnnotations().add(KubeControllerIT.pod1.getAnnotations().stream().findFirst().orElse(null));
    pod.getSelectors().add(KubeControllerIT.pod1.getSelectors().stream().findFirst().orElse(null));
    pod.getLabels().add(KubeControllerIT.pod1.getLabels().stream().findFirst().orElse(null));
    pod.getMetadata().add(KubeControllerIT.pod1.getMetadata().stream().findFirst().orElse(null));

    PodDto podDto = kubeMapper.podToPodDto(pod);
    podDto.setName(KubeControllerIT.pod1.getName());
    podDto.setNamespace(KubeControllerIT.pod1.getNamespace());

    TypedResponse<Void> response = podFeignClient.saveOrUpdate(podDto);
    assertThat(response.status(), equalTo(201));
    Long entityId = FeignUtils.getIdFromLocationHeader(response);
    assertThat(entityId, notNullValue());
  }

  @Test
  @Order(2)
  void getPodsShallReturnCorrectValueWhenValidNameAndNamespaceProvided() {

    TypedResponse<Set<PodDto>> response = podFeignClient.getAll(projectDto.getCode(), pod1.getNamespace());
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().isEmpty(), equalTo(false));
  }

  @Test
  @Order(2)
  void getPodByNameAndNamespaceShallReturnCorrectValueWhenValidNameAndNamespaceProvided() {

    TypedResponse<PodDto> response = podFeignClient.getByNameAndNamespace(pod1.getName(), pod1.getNamespace());
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getId(), notNullValue());
    assertThat(response.body().getName(), notNullValue());
    assertThat(response.body().getProject(), notNullValue());
  }

  @Test
  @Order(2)
  void shallReturnCorrectValueWhenValidPodIdProvided() {
    TypedResponse<PodDto> response = podFeignClient.getById(pod1.getId());
    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getId(), notNullValue());
    assertThat(response.body().getName(), notNullValue());
    assertThat(response.body().getProject(), notNullValue());
  }

  @Test
  @Order(3)
  void getPodDashboardSummary() {
    ensureDashboardPods();

    TypedResponse<PodSummaryDto> response = podFeignClient.getSummary(
        projectDto.getCode(),
        DASHBOARD_NAMESPACE_PREFIX,
        null,
        null,
      null,
      null);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getTotalCount(), equalTo(2L));
    assertThat(response.body().getNamespaceCount(), equalTo(2L));
    assertThat(response.body().getNodeCount(), equalTo(2L));
    assertThat(response.body().getActiveCount(), equalTo(1L));
    assertThat(response.body().getDeletedCount(), equalTo(1L));
    assertThat(response.body().getLatestSyncTime().truncatedTo(ChronoUnit.MILLIS), equalTo(dashboardPodTwo.getLastSync().truncatedTo(ChronoUnit.MILLIS)));
  }

  @Test
  @Order(3)
  void getPodDashboardSummaryWithinWindow() {
    ensureDashboardPods();

    TypedResponse<PodSummaryDto> response = podFeignClient.getSummary(
        projectDto.getCode(),
        DASHBOARD_NAMESPACE_PREFIX,
        null,
        null,
        null,
        1);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getTotalCount(), equalTo(1L));
    assertThat(response.body().getNamespaceCount(), equalTo(1L));
    assertThat(response.body().getNodeCount(), equalTo(1L));
    assertThat(response.body().getActiveCount(), equalTo(1L));
    assertThat(response.body().getDeletedCount(), equalTo(0L));
    assertThat(response.body().getLatestSyncTime().truncatedTo(ChronoUnit.MILLIS), equalTo(dashboardPodTwo.getLastSync().truncatedTo(ChronoUnit.MILLIS)));
  }

  @Test
  @Order(4)
  void getPodDashboardTrend() {
    ensureDashboardPods();

    TypedResponse<List<PodTrendPointDto>> response = podFeignClient.getTrend(
        projectDto.getCode(),
        DASHBOARD_NAMESPACE_PREFIX,
        null,
        null,
        null,
        null);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().size(), equalTo(2));
    assertThat(response.body().get(0).getPodCount(), equalTo(1L));
    assertThat(response.body().get(0).getDeletedCount(), equalTo(1L));
    assertThat(response.body().get(1).getPodCount(), equalTo(1L));
    assertThat(response.body().get(1).getActiveCount(), equalTo(1L));
  }

  @Test
  @Order(4)
  void getPodDashboardTrendWithinWindow() {
    ensureDashboardPods();

    TypedResponse<List<PodTrendPointDto>> response = podFeignClient.getTrend(
        projectDto.getCode(),
        DASHBOARD_NAMESPACE_PREFIX,
        null,
        null,
        null,
        1);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().size(), equalTo(1));
    assertThat(response.body().getFirst().getBucketStart(), equalTo(dashboardPodTwo.getLastSync().truncatedTo(ChronoUnit.DAYS)));
    assertThat(response.body().getFirst().getActiveCount(), equalTo(1L));
  }

  @Test
  @Order(5)
  void getPodDashboardInventory() {
    ensureDashboardPods();

    TypedResponse<PageResponse<PodInventoryDto>> response = podFeignClient.getAllPaged(
        0,
        10,
        "lastSync",
        "DESC",
        projectDto.getCode(),
        DASHBOARD_NAMESPACE_PREFIX,
        null,
        null,
        null);

    assertThat(response.status(), equalTo(200));
    assertThat(response.body(), notNullValue());
    assertThat(response.body().getTotalElements(), equalTo(2L));
    assertThat(response.body().getContent().size(), equalTo(2));
    assertThat(response.body().getContent().get(0).getName(), equalTo(dashboardPodTwo.getName()));
    assertThat(response.body().getContent().get(0).getStatus(), equalTo("Running"));
    assertThat(response.body().getContent().get(1).getName(), equalTo(dashboardPodOne.getName()));
    assertThat(response.body().getContent().get(1).getDeletedAt(), notNullValue());
  }

  private void ensureDashboardPods() {
    if (dashboardPodOne != null && dashboardPodTwo != null) {
      return;
    }

    final Instant base = Instant.now().truncatedTo(ChronoUnit.DAYS);
    dashboardPodOne = saveDashboardPod(
        DASHBOARD_NAMESPACE_PREFIX + "-a",
        "runtime-pod-a",
        "node-a",
        base.minus(2, ChronoUnit.DAYS).plus(9, ChronoUnit.HOURS),
        base.minus(2, ChronoUnit.DAYS).plus(11, ChronoUnit.HOURS),
        "Succeeded");
    dashboardPodTwo = saveDashboardPod(
        DASHBOARD_NAMESPACE_PREFIX + "-b",
        "runtime-pod-b",
        "node-b",
        base.minus(1, ChronoUnit.DAYS).plus(10, ChronoUnit.HOURS),
        null,
        "Running");
  }

  private PodDto saveDashboardPod(String namespace, String name, String nodeName, Instant lastSync, Instant deletedAt, String statusName) {
    Pod pod = KubeBuilder.buildPod(projectDto);
    PodDto podDto = kubeMapper.podToPodDto(pod)
        .setNamespace(namespace)
        .setName(name)
        .setNodeName(nodeName)
        .setCreatedAt(lastSync.minus(6, ChronoUnit.HOURS))
        .setDeletedAt(deletedAt)
        .setLastSync(lastSync)
        .setProject(projectDto.getCode())
        .setStatus(new PodStatusDto(statusName, statusName, null, null));

    return podService.saveOrUpdate(podDto);
  }
}