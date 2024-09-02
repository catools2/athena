package org.catools.athena.tms.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.common.exception.EntityNotFoundException;
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.ItemMetadata;
import org.catools.athena.tms.common.entity.Status;
import org.catools.athena.tms.common.entity.StatusTransition;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.repository.ItemMetadataRepository;
import org.catools.athena.tms.common.repository.ItemRepository;
import org.catools.athena.tms.common.repository.ItemTypeRepository;
import org.catools.athena.tms.common.repository.PriorityRepository;
import org.catools.athena.tms.common.repository.StatusRepository;
import org.catools.athena.tms.model.ItemDto;
import org.catools.athena.tms.model.StatusTransitionDto;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
  private final ItemMetadataRepository itemMetadataRepository;
  private final ItemTypeRepository itemTypeRepository;
  private final PriorityRepository priorityRepository;
  private final StatusRepository statusRepository;
  private final ItemRepository itemRepository;

  private final TmsMapper tmsMapper;

  @Override
  public ItemDto saveOrUpdate(final ItemDto entity) {
    log.debug("Saving entity: {}", entity);

    validateItemDtoFields(entity);

    final Item item = tmsMapper.itemDtoToItem(entity);
    final Item itemToSave = itemRepository.findByCode(item.getCode()).map(i -> {
      i.setName(item.getName());
      i.setCreatedOn(item.getCreatedOn());
      i.setCreatedBy(item.getCreatedBy());
      i.setUpdatedOn(item.getUpdatedOn());
      i.setUpdatedBy(item.getUpdatedBy());
      i.setType(item.getType());
      i.setStatus(item.getStatus());
      i.setPriority(item.getPriority());
      i.setProjectId(item.getProjectId());

      i.getMetadata().addAll(item.getMetadata());
      i.getMetadata().removeIf(d1 -> item.getMetadata().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue())));

      i.getVersionIds().addAll(item.getVersionIds());
      i.getVersionIds().removeIf(d1 -> !item.getVersionIds().contains(d1));

      item.getStatusTransitions().forEach(i::addStatusTransition);
      i.getStatusTransitions().removeIf(i1 -> item.getStatusTransitions().stream().noneMatch(i2 -> compare(i1, i2)));

      return i;
    }).orElse(item);

    itemToSave.setMetadata(normalizeMetadata(itemToSave.getMetadata()));

    final Item savedItem = itemRepository.saveAndFlush(itemToSave);
    return tmsMapper.itemToItemDto(savedItem);
  }

  @Override
  public Optional<ItemDto> getById(final Long id) {
    return itemRepository.findById(id).map(tmsMapper::itemToItemDto);
  }

  @Override
  public Optional<ItemDto> search(final String code) {
    return itemRepository.findByCode(code).map(tmsMapper::itemToItemDto);
  }

  private boolean compare(final StatusTransition s1, StatusTransition s2) {
    if (s1 == null || s2 == null)
      return s1 == s2;

    return compare(s1.getFrom(), s2.getFrom()) && compare(s1.getTo(), s2.getTo()) &&
        (s1.getOccurred() == null ? null == s2.getOccurred() : s1.getOccurred().compareTo(s2.getOccurred()) == 0);
  }

  private boolean compare(final Status s1, Status s2) {
    if (s1 == null || s2 == null)
      return s1 == s2;

    return StringUtils.equals(s1.getName(), s2.getName()) && StringUtils.equals(s1.getCode(), s2.getCode());
  }

  @SuppressWarnings("java:S2201")
  private void validateItemDtoFields(final ItemDto item) {
    Objects.requireNonNull(item.getType(), "Item type code be provided.");
    itemTypeRepository.findByCodeOrName(item.getType(), item.getType()).orElseThrow(() -> new EntityNotFoundException("item type", item.getType()));

    Objects.requireNonNull(item.getStatus(), "Item status must be provided.");
    statusRepository.findByCodeOrName(item.getStatus(), item.getStatus()).orElseThrow(() -> new EntityNotFoundException("status", item.getStatus()));

    Objects.requireNonNull(item.getPriority(), "Item priority must be provided.");
    priorityRepository.findByCodeOrName(item.getPriority(), item.getPriority()).orElseThrow(() -> new EntityNotFoundException("priority", item.getPriority()));

    Objects.requireNonNull(item.getStatusTransitions(), "Item Status Transitions must be provided.");
    for (StatusTransitionDto st : item.getStatusTransitions()) {
      Objects.requireNonNull(st, "Item Status Transition must be provided.");
      Objects.requireNonNull(st.getOccurred(), "Item Status Transition must have occurred date.");

      Objects.requireNonNull(st.getFrom(), "Item Status Transition must have from status code.");
      statusRepository.findByCodeOrName(st.getFrom(), st.getFrom()).orElseThrow(() -> new EntityNotFoundException("Status Transition - From Code", st.getFrom()));

      Objects.requireNonNull(st.getTo(), "Item Status Transition must have to status code.");
      statusRepository.findByCodeOrName(st.getTo(), st.getTo()).orElseThrow(() -> new EntityNotFoundException("Status Transition - To Code", st.getTo()));
    }
  }


  private synchronized Set<ItemMetadata> normalizeMetadata(Set<ItemMetadata> metadataSet) {
    final Set<ItemMetadata> metadata = new HashSet<>();

    for (ItemMetadata md : metadataSet) {
      // Read md from DB and if MD does not exist we create one and assign it to the pipeline
      ItemMetadata pipelineMD =
          itemMetadataRepository.findByNameAndValue(md.getName(), md.getValue())
              .orElseGet(() -> itemMetadataRepository.saveAndFlush(md));

      metadata.add(pipelineMD);
    }

    return metadata;
  }
}
