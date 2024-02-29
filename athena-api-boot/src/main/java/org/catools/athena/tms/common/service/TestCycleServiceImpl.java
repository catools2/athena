package org.catools.athena.tms.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.common.exception.EntityNotFoundException;
import org.catools.athena.core.common.repository.VersionRepository;
import org.catools.athena.tms.common.entity.TestCycle;
import org.catools.athena.tms.common.entity.TestExecution;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.repository.TestCycleRepository;
import org.catools.athena.tms.model.TestCycleDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestCycleServiceImpl implements TestCycleService {
  private final TestCycleRepository testCycleRepository;
  private final VersionRepository versionRepository;
  private final TmsMapper tmsMapper;

  @Override
  public TestCycleDto saveOrUpdate(TestCycleDto entity) {
    log.debug("Saving entity: {}", entity);
    final TestCycle cycle = tmsMapper.testCycleDtoToTestCycle(entity);

    final TestCycle entityToSave = testCycleRepository.findByCode(entity.getCode()).map(c -> {
      c.setName(cycle.getName());
      c.setVersion(cycle.getVersion());
      c.setEndDate(cycle.getEndDate());
      c.setStartDate(cycle.getStartDate());

      c.getTestExecutions().removeIf(e1 -> cycle.getTestExecutions().stream().noneMatch(getTestExecutionPredicate(e1)));

      for (TestExecution execution : cycle.getTestExecutions()) {
        Optional<TestExecution> executionFromCycle = c.getTestExecutions().stream().filter(getTestExecutionPredicate(execution)).findFirst();
        if (executionFromCycle.isEmpty()) {
          c.addTestExecution(execution);
        } else {
          executionFromCycle.ifPresent(ex -> {
            ex.setExecutor(execution.getExecutor());
            ex.setStatus(execution.getStatus());
            ex.setExecutedOn(execution.getExecutedOn());
            ex.setCreatedOn(execution.getCreatedOn());
          });
        }
      }
      return c;
    }).orElse(cycle);

    final TestCycle savedCycle = testCycleRepository.saveAndFlush(entityToSave);
    return tmsMapper.testCycleToTestCycleDto(savedCycle);
  }

  private static Predicate<TestExecution> getTestExecutionPredicate(TestExecution exec) {
    return e1 -> StringUtils.equals(e1.getItem().getCode(), exec.getItem().getCode()) &&
        isEquals(exec.getExecutedOn(), e1.getExecutedOn()) &&
        isEquals(exec.getCreatedOn(), e1.getCreatedOn());
  }

  private static boolean isEquals(Instant i1, Instant i2) {
    return i1 == null || i2 == null ?
        i1 == i2 :
        i1.truncatedTo(ChronoUnit.MILLIS).equals(i2.truncatedTo(ChronoUnit.MILLIS));
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
