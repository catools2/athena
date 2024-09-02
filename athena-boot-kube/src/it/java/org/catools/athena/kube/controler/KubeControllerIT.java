package org.catools.athena.kube.controler;

import org.catools.athena.AthenaSpringBootIT;
import org.catools.athena.configs.StagedTestData;
import org.catools.athena.core.kube.PodFeignClient;
import org.catools.athena.core.model.ProjectDto;
import org.catools.athena.kube.builder.KubeBuilder;
import org.catools.athena.kube.common.mapper.KubeMapper;
import org.catools.athena.kube.common.model.Pod;
import org.catools.athena.kube.common.service.PodService;
import org.catools.athena.kube.model.PodDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class KubeControllerIT extends AthenaSpringBootIT {
  protected PodFeignClient podFeignClient;
  static Pod pod1;
  static Pod pod2;
  static ProjectDto projectDto;

  @Autowired
  PodService podService;

  @Autowired
  KubeMapper kubeMapper;

  @BeforeAll
  public void beforeAll() {
    if (podFeignClient == null) {
      podFeignClient = testFeignBuilder.getClient(PodFeignClient.class);
    }

    projectDto = StagedTestData.getProject(1);
    pod1 = buildAndSavePod();
    pod2 = buildAndSavePod();
  }

  private Pod buildAndSavePod() {
    final PodDto pod = KubeBuilder.buildPodDto(KubeBuilder.buildPod(projectDto), projectDto);
    PodDto podDto = podService.saveOrUpdate(pod);
    return kubeMapper.podDtoToPod(podDto);
  }

}