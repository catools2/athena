package org.catools.athena.rest.tms.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.rest.tms.entity.ItemType;
import org.catools.athena.rest.tms.mapper.TmsMapper;
import org.catools.athena.rest.tms.repository.ItemTypeRepository;
import org.catools.athena.tms.model.ItemTypeDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemTypeServiceImpl implements ItemTypeService {
  private final ItemTypeRepository itemTypeRepository;
  private final TmsMapper tmsMapper;

  @Override
  public ItemTypeDto save(ItemTypeDto entity) {
    final ItemType entityToSave = tmsMapper.itemTypeDtoToItemType(entity);
    final ItemType savedRecord = itemTypeRepository.saveAndFlush(entityToSave);
    return tmsMapper.itemTypeToItemTypeDto(savedRecord);
  }

  @Override
  public Optional<ItemTypeDto> getById(Long id) {
    return itemTypeRepository.findById(id).map(tmsMapper::itemTypeToItemTypeDto);
  }

  @Override
  public Optional<ItemTypeDto> getByCode(String code) {
    return itemTypeRepository.findByCode(code).map(tmsMapper::itemTypeToItemTypeDto);
  }
}
