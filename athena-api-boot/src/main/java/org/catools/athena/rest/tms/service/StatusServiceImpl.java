package org.catools.athena.rest.tms.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.rest.tms.entity.Status;
import org.catools.athena.rest.tms.mapper.TmsMapper;
import org.catools.athena.rest.tms.repository.StatusRepository;
import org.catools.athena.tms.model.StatusDto;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;
    private final TmsMapper tmsMapper;

    @Override
    public StatusDto save(StatusDto record) {
        final Status recordToSave = tmsMapper.statusDtoToStatus(record);
        final Status savedRecord = statusRepository.saveAndFlush(recordToSave);
        return tmsMapper.statusToStatusDto(savedRecord);
    }

    @Override
    public Set<StatusDto> getAll() {
        return statusRepository.findAll().stream().map(tmsMapper::statusToStatusDto).collect(Collectors.toSet());
    }

    @Override
    public Optional<StatusDto> getById(Long id) {
        return statusRepository.findById(id).map(tmsMapper::statusToStatusDto);
    }

    @Override
    public Optional<StatusDto> getByCode(String code) {
        return statusRepository.findByCode(code).map(tmsMapper::statusToStatusDto);
    }
}
