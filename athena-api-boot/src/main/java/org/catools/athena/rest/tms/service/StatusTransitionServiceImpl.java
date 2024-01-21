package org.catools.athena.rest.tms.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.rest.common.exception.EntityNotFoundException;
import org.catools.athena.rest.tms.entity.StatusTransition;
import org.catools.athena.rest.tms.mapper.TmsMapper;
import org.catools.athena.rest.tms.repository.ItemRepository;
import org.catools.athena.rest.tms.repository.StatusRepository;
import org.catools.athena.rest.tms.repository.StatusTransitionRepository;
import org.catools.athena.tms.model.StatusTransitionDto;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatusTransitionServiceImpl implements StatusTransitionService {
  private final StatusTransitionRepository statusTransitionRepository;
  private final StatusRepository statusRepository;
  private final ItemRepository itemRepository;
  private final TmsMapper tmsMapper;

  @Override
  public StatusTransitionDto save(StatusTransitionDto record, String itemCode) {
    Objects.requireNonNull(record, "The status transition must be provided.");
    Objects.requireNonNull(record.getFrom(), "The 'From Status' code of status transition must be provided.");
    statusRepository.findByCode(record.getFrom()).orElseThrow(() -> new EntityNotFoundException("status", record.getFrom()));

    Objects.requireNonNull(record.getTo(), "The 'To Status' code of status transition must be provided.");
    statusRepository.findByCode(record.getTo()).orElseThrow(() -> new EntityNotFoundException("status", record.getTo()));

    Objects.requireNonNull(itemCode, "The 'item' code of status transition must be provided.");
    itemRepository.findByCode(itemCode).orElseThrow(() -> new EntityNotFoundException("item code", itemCode));

    final StatusTransition recordToSave = tmsMapper.statusTransitionDtoToStatusTransition(itemCode, record);
    final StatusTransition savedRecord = statusTransitionRepository.saveAndFlush(recordToSave);
    return tmsMapper.statusTransitionToStatusTransitionDto(savedRecord);
  }

  @Override
  public Set<StatusTransitionDto> getAll() {
    return statusTransitionRepository.findAll().stream().map(tmsMapper::statusTransitionToStatusTransitionDto).collect(Collectors.toSet());
  }

  @Override
  public Set<StatusTransitionDto> getAllByItemCode(String itemCode) {
    Long itemId = itemRepository.findByCode(itemCode).orElseThrow(() -> new EntityNotFoundException("item code", itemCode)).getId();
    return statusTransitionRepository.findAllByItemId(itemId).stream().map(tmsMapper::statusTransitionToStatusTransitionDto).collect(Collectors.toSet());
  }

  @Override
  public Optional<StatusTransitionDto> getById(Long id) {
    return statusTransitionRepository.findById(id).map(tmsMapper::statusTransitionToStatusTransitionDto);
  }

  @Override
  public Optional<StatusTransitionDto> findStatusTransition(StatusTransitionDto record, String itemCode) {
    Long fromId = statusRepository.findByCode(record.getFrom()).orElseThrow(() -> new EntityNotFoundException("from status", record.getFrom())).getId();
    Long toId = statusRepository.findByCode(record.getTo()).orElseThrow(() -> new EntityNotFoundException("to status", record.getTo())).getId();
    Long itemId = itemRepository.findByCode(itemCode).orElseThrow(() -> new EntityNotFoundException("item code", itemCode)).getId();

    return statusTransitionRepository.findByOccurredAndFromIdAndToIdAndItemId(record.getOccurred(), fromId, toId, itemId)
        .map(tmsMapper::statusTransitionToStatusTransitionDto);
  }
}
