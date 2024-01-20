package org.catools.athena.rest.kube.service;


import org.catools.athena.kube.model.PodDto;
import org.catools.athena.rest.common.service.BaseIdentifiableService;

import java.util.Optional;

public interface PodService extends BaseIdentifiableService<PodDto> {

    /**
     * Retrieve pod by name
     */
    Optional<PodDto> getByName(String name);
}