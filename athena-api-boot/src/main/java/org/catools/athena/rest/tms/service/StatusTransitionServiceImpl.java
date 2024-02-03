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
  private static final String ITEM_CODE = "item code";

  private final StatusTransitionRepository statusTransitionRepository;
  private final StatusRepository statusRepository;
  private final ItemRepository itemRepository;
  private final TmsMapper tmsMapper;

  @Override
  public StatusTransitionDto save(StatusTransitionDto entity, String itemCode) {
    Objects.requireNonNull(entity, "The status transition must be provided.");
    Objects.requireNonNull(entity.getFrom(), "The 'From Status' code of status transition must be provided.");
    statusRepository.findByCode(entity.getFrom()).orElseThrow(() -> new EntityNotFoundException("status", entity.getFrom()));

    Objects.requireNonNull(entity.getTo(), "The 'To Status' code of status transition must be provided.");
    statusRepository.findByCode(entity.getTo()).orElseThrow(() -> new EntityNotFoundException("status", entity.getTo()));

    Objects.requireNonNull(itemCode, "The 'item' code of status transition must be provided.");
    itemRepository.findByCode(itemCode).orElseThrow(() -> new EntityNotFoundException(ITEM_CODE, itemCode));

    final StatusTransition entityToSave = tmsMapper.statusTransitionDtoToStatusTransition(itemCode, entity);
    final StatusTransition savedRecord = statusTransitionRepository.saveAndFlush(entityToSave);
    return tmsMapper.statusTransitionToStatusTransitionDto(savedRecord);
  }

  @Override
  public Set<StatusTransitionDto> getAllByItemCode(String itemCode) {
    Long itemId = itemRepository.findByCode(itemCode).orElseThrow(() -> new EntityNotFoundException(ITEM_CODE, itemCode)).getId();
    return statusTransitionRepository.findAllByItemId(itemId).stream().map(tmsMapper::statusTransitionToStatusTransitionDto).collect(Collectors.toSet());
  }

  @Override
  public Optional<StatusTransitionDto> getById(Long id) {
    return statusTransitionRepository.findById(id).map(tmsMapper::statusTransitionToStatusTransitionDto);
  }

  @Override
  public Optional<StatusTransitionDto> findStatusTransition(StatusTransitionDto entity, String itemCode) {
    Long fromId = statusRepository.findByCode(entity.getFrom()).orElseThrow(() -> new EntityNotFoundException("from status", entity.getFrom())).getId();
    Long toId = statusRepository.findByCode(entity.getTo()).orElseThrow(() -> new EntityNotFoundException("to status", entity.getTo())).getId();
    Long itemId = itemRepository.findByCode(itemCode).orElseThrow(() -> new EntityNotFoundException(ITEM_CODE, itemCode)).getId();

    return statusTransitionRepository.findByOccurredAndFromIdAndToIdAndItemId(entity.getOccurred(), fromId, toId, itemId)
        .map(tmsMapper::statusTransitionToStatusTransitionDto);
  }
}
