package org.catools.athena.rest.tms.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.rest.common.exception.EntityNotFoundException;
import org.catools.athena.rest.core.repository.VersionRepository;
import org.catools.athena.rest.tms.entity.TestCycle;
import org.catools.athena.rest.tms.mapper.TmsMapper;
import org.catools.athena.rest.tms.repository.TestCycleRepository;
import org.catools.athena.tms.model.TestCycleDto;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestCycleServiceImpl implements TestCycleService {
  private final TestCycleRepository testCycleRepository;
  private final VersionRepository versionRepository;
  private final TmsMapper tmsMapper;

  @Override
  public TestCycleDto save(TestCycleDto entity) {
    final TestCycle entityToSave = tmsMapper.testCycleDtoToTestCycle(entity);
    final TestCycle savedRecord = testCycleRepository.saveAndFlush(entityToSave);
    return tmsMapper.testCycleToTestCycleDto(savedRecord);
  }

  @Override
  public Optional<TestCycleDto> getById(Long id) {
    return testCycleRepository.findById(id).map(tmsMapper::testCycleToTestCycleDto);
  }

  @Override
  public Optional<TestCycleDto> getByCode(String code) {
    return testCycleRepository.findByCode(code).map(tmsMapper::testCycleToTestCycleDto);
  }

  @Override
  public Set<TestCycleDto> getByVersionCode(String versionCode) {
    Long versionId = versionRepository.findByCode(versionCode).orElseThrow(() -> new EntityNotFoundException("version code", versionCode)).getId();
    return testCycleRepository.findByVersionId(versionId).stream().map(tmsMapper::testCycleToTestCycleDto).collect(Collectors.toSet());
  }
}
