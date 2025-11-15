package org.catools.athena.tms.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.common.utils.RetryUtils;
import org.catools.athena.tms.common.entity.TestCycle;
import org.catools.athena.tms.common.entity.TestExecution;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.mapper.TmsMapperService;
import org.catools.athena.tms.common.repository.TestCycleRepository;
import org.catools.athena.tms.model.TestCycleDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestCycleServiceImpl implements TestCycleService {
  private final TestCycleRepository testCycleRepository;
  private final TmsMapperService tmsMapperService;
  private final TmsMapper tmsMapper;

  @Override
  @Transactional
  public TestCycleDto saveOrUpdate(TestCycleDto entity) {
    log.debug("Saving entity: {}", entity);
    final TestCycle cycle = tmsMapper.testCycleDtoToTestCycle(entity);

    final TestCycle entityToSave = testCycleRepository.findByCode(entity.getCode()).map(c -> {
      c.setName(cycle.getName());
      c.setVersionId(cycle.getVersionId());
      c.setEndDate(cycle.getEndDate());
      c.setStartDate(cycle.getStartDate());

      c.getTestExecutions().removeIf(e1 -> cycle.getTestExecutions().stream().noneMatch(getTestExecutionPredicate(e1)));

      for (TestExecution execution : cycle.getTestExecutions()) {
        Optional<TestExecution> executionFromCycle = c.getTestExecutions().stream().filter(getTestExecutionPredicate(execution)).findFirst();
        if (executionFromCycle.isEmpty()) {
          c.addTestExecution(execution);
        } else {
          executionFromCycle.ifPresent(ex -> {
            ex.setExecutorId(execution.getExecutorId());
            ex.setStatus(execution.getStatus());
            ex.setExecutedOn(execution.getExecutedOn());
            ex.setCreatedOn(execution.getCreatedOn());
          });
        }
      }
      return c;
    }).orElse(cycle);

    final TestCycle savedCycle = RetryUtils.retry(3, 1000, integer -> testCycleRepository.saveAndFlush(entityToSave));
    return tmsMapper.testCycleToTestCycleDto(savedCycle);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<TestCycleDto> getById(Long id) {
    return testCycleRepository.findById(id).map(tmsMapper::testCycleToTestCycleDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<TestCycleDto> search(String code) {
    return testCycleRepository.findByCode(code).map(tmsMapper::testCycleToTestCycleDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<TestCycleDto> findLastByPattern(String name, String versionCode) {
    Long versionId = tmsMapperService.getVersionId(versionCode);
    return testCycleRepository.findTop1ByNameLikeAndVersionIdOrderByIdDesc(name, versionId).map(tmsMapper::testCycleToTestCycleDto);
  }

  private static boolean isEquals(Instant i1, Instant i2) {
    return i1 == null || i2 == null ?
        i1 == i2 :
        i1.truncatedTo(ChronoUnit.MILLIS).equals(i2.truncatedTo(ChronoUnit.MILLIS));
  }

  private static Predicate<TestExecution> getTestExecutionPredicate(TestExecution exec) {
    return e1 -> StringUtils.equals(e1.getItem().getCode(), exec.getItem().getCode()) &&
        isEquals(exec.getExecutedOn(), e1.getExecutedOn()) &&
        isEquals(exec.getCreatedOn(), e1.getCreatedOn());
  }
}
