package org.catools.athena.rest.feign.kube.clients;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.model.kube.PodDto;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;

import static org.catools.athena.rest.feign.common.utils.FeignUtils.getClient;

@Slf4j
@UtilityClass
@SuppressWarnings("unused")
public class KubeClient {

  private static final PodClient POD_CLIENT = getClient(PodClient.class, CoreConfigs.getAthenaHost());

  public static void savePod(PodDto pod) {
    POD_CLIENT.saveOrUpdate(pod);
  }

}
