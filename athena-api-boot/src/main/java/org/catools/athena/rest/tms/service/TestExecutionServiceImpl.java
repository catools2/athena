package org.catools.athena.rest.tms.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.catools.athena.rest.common.exception.EntityNotFoundException;
import org.catools.athena.rest.tms.entity.Item;
import org.catools.athena.rest.tms.entity.TestCycle;
import org.catools.athena.rest.tms.entity.TestExecution;
import org.catools.athena.rest.tms.mapper.TmsMapper;
import org.catools.athena.rest.tms.repository.ItemRepository;
import org.catools.athena.rest.tms.repository.TestCycleRepository;
import org.catools.athena.rest.tms.repository.TestExecutionRepository;
import org.catools.athena.tms.model.TestExecutionDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestExecutionServiceImpl implements TestExecutionService {
    private final TestExecutionRepository testExecutionRepository;
    private final TestCycleRepository testCycleRepository;
    private final ItemRepository itemRepository;
    private final TmsMapper tmsMapper;

    @Override
    public TestExecutionDto save(TestExecutionDto record) {
        final TestExecution recordToSave = tmsMapper.testExecutionDtoToTestExecution(record);
        final TestExecution savedRecord = testExecutionRepository.saveAndFlush(recordToSave);
        return tmsMapper.testExecutionToTestExecutionDto(savedRecord);
    }

    @Override
    public Set<TestExecutionDto> getAll() {
        return testExecutionRepository.findAll().stream().map(tmsMapper::testExecutionToTestExecutionDto).collect(Collectors.toSet());
    }

    @Override
    public Optional<TestExecutionDto> getById(Long id) {
        return testExecutionRepository.findById(id).map(tmsMapper::testExecutionToTestExecutionDto);
    }

    @Override
    public Optional<TestExecutionDto> getByCode(String code) {
        throw new NotImplementedException("No implementation for getByCode for TestExecution!");
    }

    @Override
    public Optional<TestExecutionDto> getByCreatedOnItemCodeAndCycleCode(Instant createdOn, String itemCode, String cycleCode) {
        Long itemId = itemRepository.findByCode(itemCode).orElseThrow(() -> new EntityNotFoundException("item code", itemCode)).getId();
        Long cycleId = testCycleRepository.findByCode(cycleCode).orElseThrow(() -> new EntityNotFoundException("cycle code", cycleCode)).getId();
        return testExecutionRepository.findByCreatedOnAndCycleIdAndItemId(createdOn, cycleId, itemId).map(tmsMapper::testExecutionToTestExecutionDto);
    }

    @Override
    public Set<TestExecutionDto> getAll(@Nullable String itemCode, @Nullable String cycleCode) {
        Optional<Item> itemByCode = itemRepository.findByCode(itemCode);
        Optional<TestCycle> cycleByCode = testCycleRepository.findByCode(cycleCode);

        if (itemCode != null && cycleCode != null) {
            Long itemId = itemByCode.orElseThrow(() -> new EntityNotFoundException("item code", itemCode)).getId();
            Long cycleId = cycleByCode.orElseThrow(() -> new EntityNotFoundException("cycle code", cycleCode)).getId();
            return testExecutionRepository.findByCycleIdAndItemId(cycleId, itemId).stream().map(tmsMapper::testExecutionToTestExecutionDto).collect(Collectors.toSet());
        } else if (itemCode != null) {
            Long itemId = itemByCode.orElseThrow(() -> new EntityNotFoundException("item code", itemCode)).getId();
            return testExecutionRepository.findByItemId(itemId).stream().map(tmsMapper::testExecutionToTestExecutionDto).collect(Collectors.toSet());
        } else if (cycleCode != null) {
            Long cycleId = cycleByCode.orElseThrow(() -> new EntityNotFoundException("cycle code", cycleCode)).getId();
            return testExecutionRepository.findByCycleId(cycleId).stream().map(tmsMapper::testExecutionToTestExecutionDto).collect(Collectors.toSet());
        } else {
            return testExecutionRepository.findAll().stream().map(tmsMapper::testExecutionToTestExecutionDto).collect(Collectors.toSet());
        }
    }
}
