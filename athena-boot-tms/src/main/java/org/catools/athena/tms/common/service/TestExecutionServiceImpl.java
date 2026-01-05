package org.catools.athena.tms.common.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.exception.EntityNotFoundException;
import org.catools.athena.common.exception.RecordNotFoundException;
import org.catools.athena.common.utils.RetryUtils;
import org.catools.athena.model.tms.TestExecutionDto;
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.TestCycle;
import org.catools.athena.tms.common.entity.TestExecution;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.repository.ItemRepository;
import org.catools.athena.tms.common.repository.TestCycleRepository;
import org.catools.athena.tms.common.repository.TestExecutionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestExecutionServiceImpl implements TestExecutionService {
  private static final String ITEM_CODE = "item code";
  private static final String CYCLE_CODE = "cycle code";

  private final TestExecutionRepository testExecutionRepository;
  private final TestCycleRepository testCycleRepository;
  private final ItemRepository itemRepository;
  private final TmsMapper tmsMapper;

  @Override
  @Transactional
  @SuppressWarnings("java:S2201")
  public TestExecutionDto save(String cycleCode, TestExecutionDto entity) {
    TestCycle testCycle = testCycleRepository.findByCode(cycleCode).orElseThrow(() -> new RecordNotFoundException("cycle", "code", cycleCode));
    final TestExecution entityToSave = tmsMapper.testExecutionDtoToTestExecution(testCycle, entity);
    final TestExecution savedRecord = RetryUtils.retry(3, 1000, integer -> testExecutionRepository.saveAndFlush(entityToSave));
    return tmsMapper.testExecutionToTestExecutionDto(savedRecord);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<TestExecutionDto> getById(Long id) {
    return testExecutionRepository.findById(id).map(tmsMapper::testExecutionToTestExecutionDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Set<TestExecutionDto> getAll(@Nullable String itemCode, @Nullable String cycleCode) {
    Optional<Item> itemByCode = itemRepository.findByCode(itemCode);
    Optional<TestCycle> cycleByCode = testCycleRepository.findByCode(cycleCode);

    if (itemCode != null && cycleCode != null) {
      Long itemId = itemByCode.orElseThrow(() -> new EntityNotFoundException(ITEM_CODE, itemCode)).getId();
      Long cycleId = cycleByCode.orElseThrow(() -> new EntityNotFoundException(CYCLE_CODE, cycleCode)).getId();
      return testExecutionRepository.findByCycleIdAndItemId(cycleId, itemId).stream().map(tmsMapper::testExecutionToTestExecutionDto).collect(Collectors.toSet());
    } else if (itemCode != null) {
      Long itemId = itemByCode.orElseThrow(() -> new EntityNotFoundException(ITEM_CODE, itemCode)).getId();
      return testExecutionRepository.findByItemId(itemId).stream().map(tmsMapper::testExecutionToTestExecutionDto).collect(Collectors.toSet());
    } else if (cycleCode != null) {
      Long cycleId = cycleByCode.orElseThrow(() -> new EntityNotFoundException(CYCLE_CODE, cycleCode)).getId();
      return testExecutionRepository.findByCycleId(cycleId).stream().map(tmsMapper::testExecutionToTestExecutionDto).collect(Collectors.toSet());
    } else {
      return testExecutionRepository.findAll().stream().map(tmsMapper::testExecutionToTestExecutionDto).collect(Collectors.toSet());
    }

  }
}
