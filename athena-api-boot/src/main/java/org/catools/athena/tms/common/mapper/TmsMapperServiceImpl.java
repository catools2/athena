package org.catools.athena.tms.common.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.catools.athena.tms.common.entity.Item;
import org.catools.athena.tms.common.entity.ItemType;
import org.catools.athena.tms.common.entity.Priority;
import org.catools.athena.tms.common.entity.Status;
import org.catools.athena.tms.common.repository.ItemRepository;
import org.catools.athena.tms.common.repository.ItemTypeRepository;
import org.catools.athena.tms.common.repository.PriorityRepository;
import org.catools.athena.tms.common.repository.StatusRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TmsMapperServiceImpl implements TmsMapperService {

  private final ItemRepository itemRepository;
  private final ItemTypeRepository itemTypeRepository;
  private final StatusRepository statusRepository;
  private final PriorityRepository priorityRepository;

  @Cacheable(value = "itemCache", key = "#code", condition = "#code!=null", unless = "#result == null")
  @Override
  public Item getItemByCode(String code) {
    if (StringUtils.isBlank(code)) return null;
    return itemRepository.findByCode(code).orElse(null);
  }

  @Cacheable(value = "itemTypeCache", key = "#code", condition = "#code!=null", unless = "#result == null")
  @Override
  public ItemType getItemTypeByCode(String code) {
    if (StringUtils.isBlank(code)) return null;
    return itemTypeRepository.findByCode(code).orElse(null);
  }

  @Cacheable(value = "statusCache", key = "#code", condition = "#code!=null", unless = "#result == null")
  @Override
  public Status getStatusByCode(String code) {
    if (StringUtils.isBlank(code)) return null;
    return statusRepository.findByCode(code).orElse(null);
  }

  @Cacheable(value = "priorityCache", key = "#code", condition = "#code!=null", unless = "#result == null")
  @Override
  public Priority getPriorityByCode(String code) {
    if (StringUtils.isBlank(code)) return null;
    return priorityRepository.findByCode(code).orElse(null);
  }

  @CacheEvict(value = {"priorityCache", "itemCache", "cycleCache", "statusCache", "itemTypeCache"}, allEntries = true)
  @Scheduled(fixedRate = 60 * 1000)
  @SuppressWarnings("unused")
  public void emptyCache() {
    log.debug("Emptying TSM mapper cache");
  }

}
