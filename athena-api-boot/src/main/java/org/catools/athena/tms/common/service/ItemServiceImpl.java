package org.catools.athena.tms.common.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.common.exception.EntityNotFoundException;
import org.catools.athena.core.common.repository.ProjectRepository;
import org.catools.athena.core.common.repository.VersionRepository;
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.repository.*;
import org.catools.athena.tms.model.ItemDto;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.catools.athena.core.utils.MetadataPersistentHelper.normalizeMetadata;

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
  public ItemDto saveOrUpdate(final ItemDto entity) {
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
      i.setProject(item.getProject());

      i.getMetadata().removeIf(d1 -> item.getMetadata().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue())));
      i.getMetadata().addAll(item.getMetadata().stream().filter(d1 -> i.getMetadata().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getValue().equals(d2.getValue()))).collect(Collectors.toSet()));

      i.getVersions().removeIf(d1 -> item.getVersions().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getCode().equals(d2.getCode())));
      i.getVersions().addAll(item.getVersions().stream().filter(d1 -> i.getVersions().stream().noneMatch(d2 -> d1.getName().equals(d2.getName()) && d1.getCode().equals(d2.getCode()))).collect(Collectors.toSet()));

      return i;
    }).orElse(item);

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
