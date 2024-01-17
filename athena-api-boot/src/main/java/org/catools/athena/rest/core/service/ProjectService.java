package org.catools.athena.rest.core.service;


import org.catools.athena.core.model.ProjectDto;

import java.util.Optional;
import java.util.Set;


public interface ProjectService {

    /**
     * Returns a list of all available projects.
     */
    Set<ProjectDto> getProjects();

    /**
     * Get project by code
     */
    Optional<ProjectDto> getProjectByCode(String code);

    /**
     * Get project by id
     */
    Optional<ProjectDto> getProjectById(Long id);

    /**
     * Save project
     */
    ProjectDto save(ProjectDto project);

}