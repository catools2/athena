package org.catools.athena.rest.core.mapper;

import lombok.RequiredArgsConstructor;
import org.catools.athena.rest.core.entity.Environment;
import org.catools.athena.rest.core.entity.Project;
import org.catools.athena.rest.core.entity.User;
import org.catools.athena.rest.core.entity.Version;
import org.catools.athena.rest.core.repository.EnvironmentRepository;
import org.catools.athena.rest.core.repository.ProjectRepository;
import org.catools.athena.rest.core.repository.UserRepository;
import org.catools.athena.rest.core.repository.VersionRepository;
import org.catools.athena.rest.pipeline.entity.Pipeline;
import org.catools.athena.rest.pipeline.repository.PipelineRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoreMapperServiceImpl implements CoreMapperService {

    private final PipelineRepository pipelineRepository;
    private final EnvironmentRepository environmentRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final VersionRepository versionRepository;

    @SuppressWarnings("notused")
    @Override
    public Project getProjectByCode(String code) {
        return projectRepository.findByCode(code).orElse(null);
    }

    @Override
    public Environment getEnvironmentByCode(String code) {
        return environmentRepository.findByCode(code).orElse(null);
    }

    @Override
    public Pipeline getPipelineById(Long pipelineId) {
        return pipelineRepository.findById(pipelineId).orElse(null);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findByName(name).orElse(null);
    }

    @Override
    public Version getVersionByCode(String code) {
        return versionRepository.findByCode(code).orElse(null);
    }

    @Override
    public Set<Version> getVersionsByCode(Set<String> codes) {
        return codes.stream().map(this::getVersionByCode).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getVersionCodesFromVersions(Set<Version> versions) {
        return versions.stream().map(Version::getCode).collect(Collectors.toSet());
    }
}