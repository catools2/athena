package org.catools.athena.tms.common.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.catools.athena.common.exception.EntityNotFoundException;
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.TestCycle;
import org.catools.athena.tms.common.entity.TestExecution;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.repository.ItemRepository;
import org.catools.athena.tms.common.repository.TestCycleRepository;
import org.catools.athena.tms.common.repository.TestExecutionRepository;
import org.catools.athena.tms.model.TestExecutionDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
  public TestExecutionDto save(TestExecutionDto entity) {
    final TestExecution entityToSave = tmsMapper.testExecutionDtoToTestExecution(entity);
    final TestExecution savedRecord = testExecutionRepository.saveAndFlush(entityToSave);
    return tmsMapper.testExecutionToTestExecutionDto(savedRecord);
  }

  @Override
  public Optional<TestExecutionDto> getById(Long id) {
    return testExecutionRepository.findById(id).map(tmsMapper::testExecutionToTestExecutionDto);
  }

  @Override
  public Optional<TestExecutionDto> getByCreatedOnAndItemCodeAndCycleCode(Instant createdOn, String itemCode, String cycleCode) {
    Long itemId = itemRepository.findByCode(itemCode).orElseThrow(() -> new EntityNotFoundException(ITEM_CODE, itemCode)).getId();
    Long cycleId = testCycleRepository.findByCode(cycleCode).orElseThrow(() -> new EntityNotFoundException(CYCLE_CODE, cycleCode)).getId();
    return testExecutionRepository.findByCreatedOnAndCycleIdAndItemId(createdOn, cycleId, itemId).map(tmsMapper::testExecutionToTestExecutionDto);
  }

  @Override
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