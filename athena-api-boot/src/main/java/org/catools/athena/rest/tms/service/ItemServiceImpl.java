package org.catools.athena.rest.tms.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.rest.common.exception.EntityNotFoundException;
import org.catools.athena.rest.core.repository.ProjectRepository;
import org.catools.athena.rest.core.repository.VersionRepository;
import org.catools.athena.rest.tms.entity.Item;
import org.catools.athena.rest.tms.mapper.TmsMapper;
import org.catools.athena.rest.tms.repository.*;
import org.catools.athena.tms.model.ItemDto;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static org.catools.athena.rest.core.utils.MetadataPersistentHelper.normalizeMetadata;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
  private final ItemMetadataRepository itemMetadataRepository;
  private final ItemTypeRepository itemTypeRepository;
  private final PriorityRepository priorityRepository;
  private final ProjectRepository projectRepository;
  private final VersionRepository versionRepository;
  private final StatusRepository statusRepository;

  private final ItemRepository itemRepository;

  private final TmsMapper tmsMapper;

  @Override
  public ItemDto save(final ItemDto item) {
    validateItemDtoFields(item);
    final Item itemToSave = tmsMapper.itemDtoToItem(item);
    itemToSave.setMetadata(normalizeMetadata(itemToSave.getMetadata(), itemMetadataRepository));
    final Item savedItem = itemRepository.saveAndFlush(itemToSave);
    return tmsMapper.itemToItemDto(savedItem);
  }

  @Override
  public Optional<ItemDto> getById(final Long id) {
    return itemRepository.findById(id).map(tmsMapper::itemToItemDto);
  }

  @Override
  public Optional<ItemDto> getByCode(final String code) {
    return itemRepository.findByCode(code).map(tmsMapper::itemToItemDto);
  }

  private void validateItemDtoFields(final ItemDto item) {
    Objects.requireNonNull(item.getType(), "Item type code be provided.");
    itemTypeRepository.findByCode(item.getType()).orElseThrow(() -> new EntityNotFoundException("item type", item.getType()));

    Objects.requireNonNull(item.getStatus(), "Item status must be provided.");
    statusRepository.findByCode(item.getStatus()).orElseThrow(() -> new EntityNotFoundException("status", item.getStatus()));

    Objects.requireNonNull(item.getPriority(), "Item priority must be provided.");
    priorityRepository.findByCode(item.getPriority()).orElseThrow(() -> new EntityNotFoundException("priority", item.getPriority()));

    Objects.requireNonNull(item.getProject(), "Item project must be provided.");
    projectRepository.findByCode(item.getProject()).orElseThrow(() -> new EntityNotFoundException("project", item.getProject()));

    Objects.requireNonNull(item.getVersions(), "Item project must be provided.");
    for (String versionCode : item.getVersions()) {
      Objects.requireNonNull(versionCode, "Item version code must be provided.");
      versionRepository.findByCode(versionCode).orElseThrow(() -> new EntityNotFoundException("version", versionCode));
    }
  }
}
