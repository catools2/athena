package org.catools.athena.tms.common.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.common.exception.EntityNotFoundException;
import org.catools.athena.core.common.repository.VersionRepository;
import org.catools.athena.tms.common.entity.TestCycle;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.repository.TestCycleRepository;
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
  public TestCycleDto saveOrUpdate(TestCycleDto entity) {
    final TestCycle cycle = tmsMapper.testCycleDtoToTestCycle(entity);

    final TestCycle entityToSave = testCycleRepository.findByCode(entity.getCode()).map(c -> {
      c.setName(cycle.getName());
      c.setVersion(cycle.getVersion());
      c.setEndDate(cycle.getEndDate());
      c.setStartDate(cycle.getStartDate());
      return c;
    }).orElse(cycle);

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
