package org.catools.athena.rest.kube.service;


import org.catools.athena.kube.model.ContainerStateDto;
import org.catools.athena.rest.common.service.BaseIdentifiableService;

import java.util.Optional;
import java.util.Set;

public interface ContainerStateService extends BaseIdentifiableService<ContainerStateDto> {

    /**
     * Retrieve container by name
     */
    Optional<ContainerStateDto> getByName(String name);

    /**
     * Retrieve all container for specific pod
     */
    Set<ContainerStateDto> getByContainerId(Long containerId);
}