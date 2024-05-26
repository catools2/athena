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

  @Cacheable(value = "itemTypeCache", key = "#keyword", condition = "#keyword!=null", unless = "#result == null")
  @Override
  public ItemType getItemType(String keyword) {
    if (StringUtils.isBlank(keyword)) return null;
    return itemTypeRepository.findByCodeOrName(keyword, keyword).orElse(null);
  }

  @Cacheable(value = "statusCache", key = "#keyword", condition = "#keyword!=null", unless = "#result == null")
  @Override
  public Status getStatus(String keyword) {
    if (StringUtils.isBlank(keyword)) return null;
    return statusRepository.findByCodeOrName(keyword, keyword).orElse(null);
  }

  @Cacheable(value = "priorityCache", key = "#keyword", condition = "#keyword!=null", unless = "#result == null")
  @Override
  public Priority getPriority(String keyword) {
    if (StringUtils.isBlank(keyword)) return null;
    return priorityRepository.findByCodeOrName(keyword, keyword).orElse(null);
  }

  @CacheEvict(value = {"priorityCache", "itemCache", "cycleCache", "statusCache", "itemTypeCache"}, allEntries = true)
  @Scheduled(fixedRate = 60 * 1000)
  public void emptyCache() {
    // this is a scheduled timer to clean up caches
  }

}
