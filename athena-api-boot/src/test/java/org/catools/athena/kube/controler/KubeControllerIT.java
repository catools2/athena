package org.catools.athena.kube.controler;

import org.catools.athena.core.controller.CoreControllerIT;
import org.catools.athena.kube.builder.KubeBuilder;
import org.catools.athena.kube.common.mapper.KubeMapper;
import org.catools.athena.kube.common.model.Pod;
import org.catools.athena.kube.common.repository.ContainerMetadataRepository;
import org.catools.athena.kube.common.repository.ContainerRepository;
import org.catools.athena.kube.common.repository.PodAnnotationRepository;
import org.catools.athena.kube.common.repository.PodLabelRepository;
import org.catools.athena.kube.common.repository.PodMetadataRepository;
import org.catools.athena.kube.common.repository.PodRepository;
import org.catools.athena.kube.common.repository.PodSelectorRepository;
import org.catools.athena.kube.common.repository.PodStatusRepository;
import org.catools.athena.kube.common.service.PodService;
import org.catools.athena.kube.model.PodDto;
import org.catools.athena.kube.rest.controler.PodController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class KubeControllerIT extends CoreControllerIT {
  static Pod POD;
  static Pod POD2;

  @Autowired
  PodRepository podRepository;

  @Autowired
  ContainerRepository containerRepository;

  @Autowired
  ContainerMetadataRepository containerMetadataRepository;

  @Autowired
  PodStatusRepository podStatusRepository;

  @Autowired
  PodMetadataRepository podMetadataRepository;

  @Autowired
  PodAnnotationRepository podAnnotationRepository;

  @Autowired
  PodLabelRepository podLabelRepository;

  @Autowired
  PodSelectorRepository podSelectorRepository;

  @Autowired
  PodController podController;

  @Autowired
  PodService podService;

  @Autowired
  KubeMapper kubeMapper;

  @BeforeAll
  public void beforeAll() {
    POD = buildAndSavePod();
    POD2 = buildAndSavePod();
  }

  private Pod buildAndSavePod() {
    final PodDto pod = KubeBuilder.buildPodDto(KubeBuilder.buildPod(PROJECT));
    PodDto podDto = podService.saveOrUpdate(pod);
    return kubeMapper.podDtoToPod(podDto);
  }

}