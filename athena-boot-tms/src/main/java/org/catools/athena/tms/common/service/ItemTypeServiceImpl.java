package org.catools.athena.tms.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.common.utils.RetryUtils;
import org.catools.athena.model.tms.ItemTypeDto;
import org.catools.athena.tms.common.entity.ItemType;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.repository.ItemTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemTypeServiceImpl implements ItemTypeService {
  private final ItemTypeRepository itemTypeRepository;
  private final TmsMapper tmsMapper;

  @Override
  @Transactional
  public ItemTypeDto saveOrUpdate(ItemTypeDto entity) {
    log.debug("Saving entity: {}", entity);
    final ItemType entityToSave = itemTypeRepository.findByCodeOrName(entity.getCode(), entity.getName()).map(i -> {
      i.setCode(entity.getCode());
      i.setName(entity.getName());
      return i;
    }).orElseGet(() -> tmsMapper.itemTypeDtoToItemType(entity));

    final ItemType savedRecord = RetryUtils.retry(3, 1000, integer -> itemTypeRepository.saveAndFlush(entityToSave));
    return tmsMapper.itemTypeToItemTypeDto(savedRecord);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ItemTypeDto> getById(Long id) {
    return itemTypeRepository.findById(id).map(tmsMapper::itemTypeToItemTypeDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ItemTypeDto> search(String keyword) {
    return itemTypeRepository.findByCodeOrName(keyword, keyword).map(tmsMapper::itemTypeToItemTypeDto);
  }
}
