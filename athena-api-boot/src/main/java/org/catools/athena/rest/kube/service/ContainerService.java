package org.catools.athena.rest.kube.service;


import org.catools.athena.kube.model.ContainerDto;
import org.catools.athena.rest.common.service.BaseIdentifiableService;

import java.util.Optional;
import java.util.Set;

public interface ContainerService extends BaseIdentifiableService<ContainerDto> {

    /**
     * Retrieve container by name
     */
    Optional<ContainerDto> getByName(String name);

    /**
     * Retrieve all container for specific pod
     */
    Set<ContainerDto> getByPodId(Long podId);
}