package org.catools.athena.tms.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.common.exception.EntityNotFoundException;
import org.catools.athena.common.utils.RetryUtils;
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
import org.springframework.transaction.annotation.Transactional;

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
  @Transactional
  public ItemDto saveOrUpdate(final ItemDto entity) {
    log.debug("Saving entity: {}", entity);

    validateItemDtoFields(entity);

    final Item item = tmsMapper.itemDtoToItem(entity);
    final Item itemToSave = itemRepository.findByCode(item.getCode())
        .map(existingItem -> updateExistingItem(existingItem, item))
        .orElse(item);

    itemToSave.setMetadata(normalizeMetadata(itemToSave.getMetadata()));

    final Item savedItem = RetryUtils.retry(3, 1000, attempt -> itemRepository.saveAndFlush(itemToSave));
    return tmsMapper.itemToItemDto(savedItem);
  }

  /**
   * Updates an existing item with new values
   */
  private Item updateExistingItem(final Item existing, final Item updates) {
    existing.setName(updates.getName());
    existing.setCreatedOn(updates.getCreatedOn());
    existing.setCreatedBy(updates.getCreatedBy());
    existing.setUpdatedOn(updates.getUpdatedOn());
    existing.setUpdatedBy(updates.getUpdatedBy());
    existing.setType(updates.getType());
    existing.setStatus(updates.getStatus());
    existing.setPriority(updates.getPriority());
    existing.setProjectId(updates.getProjectId());

    syncMetadata(existing, updates);
    syncVersionIds(existing, updates);
    syncStatusTransitions(existing, updates);

    return existing;
  }

  /**
   * Synchronizes metadata between existing and updated items
   */
  private void syncMetadata(final Item existing, final Item updates) {
    if (updates.getMetadata() == null || updates.getMetadata().isEmpty()) {
      existing.getMetadata().clear();
      return;
    }

    if (existing.getMetadata() == null) {
      existing.setMetadata(new HashSet<>());
    }

    existing.getMetadata().addAll(updates.getMetadata());
    existing.getMetadata().removeIf(d1 ->
        updates.getMetadata().stream()
            .noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue()))
    );
  }

  /**
   * Synchronizes version IDs between existing and updated items
   */
  private void syncVersionIds(final Item existing, final Item updates) {
    if (updates.getVersionIds() == null || updates.getVersionIds().isEmpty()) {
      existing.getVersionIds().clear();
      return;
    }

    if (existing.getVersionIds() == null) {
      existing.setVersionIds(new HashSet<>());
    }

    existing.getVersionIds().addAll(updates.getVersionIds());
    existing.getVersionIds().removeIf(id -> !updates.getVersionIds().contains(id));
  }

  /**
   * Synchronizes status transitions between existing and updated items
   */
  private void syncStatusTransitions(final Item existing, final Item updates) {
    if (updates.getStatusTransitions() == null || updates.getStatusTransitions().isEmpty()) {
      existing.getStatusTransitions().clear();
      return;
    }

    if (existing.getStatusTransitions() == null) {
      existing.setStatusTransitions(new HashSet<>());
    }

    updates.getStatusTransitions().forEach(existing::addStatusTransition);
    existing.getStatusTransitions().removeIf(st1 ->
        updates.getStatusTransitions().stream()
            .noneMatch(st2 -> compareStatusTransitions(st1, st2))
    );
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ItemDto> getById(final Long id) {
    return itemRepository.findById(id).map(tmsMapper::itemToItemDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ItemDto> search(final String code) {
    return itemRepository.findByCode(code).map(tmsMapper::itemToItemDto);
  }

  /**
   * Compares two status transitions for equality
   */
  private boolean compareStatusTransitions(final StatusTransition s1, final StatusTransition s2) {
    if (s1 == null || s2 == null) {
      return s1 == s2;
    }

    return compareStatuses(s1.getFrom(), s2.getFrom())
        && compareStatuses(s1.getTo(), s2.getTo())
        && Objects.equals(s1.getOccurred(), s2.getOccurred());
  }

  /**
   * Compares two statuses for equality
   */
  private boolean compareStatuses(final Status s1, final Status s2) {
    if (s1 == null || s2 == null) {
      return s1 == s2;
    }

    return StringUtils.equals(s1.getName(), s2.getName())
        && StringUtils.equals(s1.getCode(), s2.getCode());
  }

  @SuppressWarnings("java:S2201")
  private void validateItemDtoFields(final ItemDto item) {
    Objects.requireNonNull(item.getType(), "Item type must be provided.");
    itemTypeRepository.findByCodeOrName(item.getType(), item.getType())
        .orElseThrow(() -> new EntityNotFoundException("item type", item.getType()));

    Objects.requireNonNull(item.getStatus(), "Item status must be provided.");
    statusRepository.findByCodeOrName(item.getStatus(), item.getStatus())
        .orElseThrow(() -> new EntityNotFoundException("status", item.getStatus()));

    Objects.requireNonNull(item.getPriority(), "Item priority must be provided.");
    priorityRepository.findByCodeOrName(item.getPriority(), item.getPriority())
        .orElseThrow(() -> new EntityNotFoundException("priority", item.getPriority()));

    Objects.requireNonNull(item.getStatusTransitions(), "Item Status Transitions must be provided.");
    validateStatusTransitions(item.getStatusTransitions());
  }

  /**
   * Validates status transitions
   */
  private void validateStatusTransitions(final Set<StatusTransitionDto> statusTransitions) {
    for (StatusTransitionDto st : statusTransitions) {
      Objects.requireNonNull(st, "Item Status Transition must be provided.");
      Objects.requireNonNull(st.getOccurred(), "Item Status Transition must have occurred date.");

      Objects.requireNonNull(st.getFrom(), "Item Status Transition must have from status code.");
      statusRepository.findByCodeOrName(st.getFrom(), st.getFrom())
          .orElseThrow(() -> new EntityNotFoundException("Status Transition - From Code", st.getFrom()));

      Objects.requireNonNull(st.getTo(), "Item Status Transition must have to status code.");
      statusRepository.findByCodeOrName(st.getTo(), st.getTo())
          .orElseThrow(() -> new EntityNotFoundException("Status Transition - To Code", st.getTo()));
    }
  }

  /**
   * Normalizes metadata by ensuring all metadata entities exist in the database.
   * If metadata doesn't exist, it creates a new entry.
   * Database-level constraints ensure consistency in concurrent scenarios.
   *
   * @param metadataSet the set of metadata to normalize
   * @return normalized set of metadata
   */
  private Set<ItemMetadata> normalizeMetadata(final Set<ItemMetadata> metadataSet) {
    if (metadataSet == null || metadataSet.isEmpty()) {
      return new HashSet<>();
    }

    final Set<ItemMetadata> metadata = new HashSet<>();

    for (ItemMetadata md : metadataSet) {
      // Read metadata from DB and if it doesn't exist, create one
      ItemMetadata itemMetadata = itemMetadataRepository
          .findByNameAndValue(md.getName(), md.getValue())
          .orElseGet(() -> itemMetadataRepository.saveAndFlush(md));

      metadata.add(itemMetadata);
    }

    return metadata;
  }
}
