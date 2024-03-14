package org.catools.athena.tms.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.exception.EntityNotFoundException;
import org.catools.athena.common.utils.RetryUtil;
import org.catools.athena.tms.common.entity.StatusTransition;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.repository.ItemRepository;
import org.catools.athena.tms.common.repository.StatusRepository;
import org.catools.athena.tms.common.repository.StatusTransitionRepository;
import org.catools.athena.tms.model.StatusTransitionDto;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
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
    statusRepository.findByCodeOrName(entity.getFrom(), entity.getFrom()).orElseThrow(() -> new EntityNotFoundException("status", entity.getFrom()));

    Objects.requireNonNull(entity.getTo(), "The 'To Status' code of status transition must be provided.");
    statusRepository.findByCodeOrName(entity.getTo(), entity.getTo()).orElseThrow(() -> new EntityNotFoundException("status", entity.getTo()));

    Objects.requireNonNull(itemCode, "The 'item' code of status transition must be provided.");
    itemRepository.findByCode(itemCode).orElseThrow(() -> new EntityNotFoundException(ITEM_CODE, itemCode));

    final StatusTransition entityToSave = tmsMapper.statusTransitionDtoToStatusTransition(entity, itemCode);
    final StatusTransition savedRecord = RetryUtil.retry(3, 1000, integer -> statusTransitionRepository.saveAndFlush(entityToSave));
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
    Long fromId = statusRepository.findByCodeOrName(entity.getFrom(), entity.getFrom()).orElseThrow(() -> new EntityNotFoundException("from status", entity.getFrom())).getId();
    Long toId = statusRepository.findByCodeOrName(entity.getTo(), entity.getTo()).orElseThrow(() -> new EntityNotFoundException("to status", entity.getTo())).getId();
    Long itemId = itemRepository.findByCode(itemCode).orElseThrow(() -> new EntityNotFoundException(ITEM_CODE, itemCode)).getId();

    return statusTransitionRepository.findByOccurredAndFromIdAndToIdAndItemId(entity.getOccurred(), fromId, toId, itemId)
        .map(tmsMapper::statusTransitionToStatusTransitionDto);
  }
}
