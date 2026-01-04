package org.catools.athena.rest.feign.tms.clients;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.model.tms.ItemDto;
import org.catools.athena.model.tms.ItemTypeDto;
import org.catools.athena.model.tms.PriorityDto;
import org.catools.athena.model.tms.StatusDto;
import org.catools.athena.model.tms.SyncInfoDto;
import org.catools.athena.model.tms.TestCycleDto;
import org.catools.athena.rest.feign.core.configs.CoreConfigs;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.catools.athena.rest.feign.common.utils.FeignUtils.getClient;

@Slf4j
@UtilityClass
@SuppressWarnings("unused")
public class TmsClient {

  private static final ItemClient ITEM_CLIENT = getClient(ItemClient.class, CoreConfigs.getAthenaHost());
  private static final ItemTypeClient ITEM_TYPE_CLIENT = getClient(ItemTypeClient.class, CoreConfigs.getAthenaHost());
  private static final StatusClient STATUS_CLIENT = getClient(StatusClient.class, CoreConfigs.getAthenaHost());
  private static final PriorityClient PRIORITY_CLIENT = getClient(PriorityClient.class, CoreConfigs.getAthenaHost());
  private static final SyncInfoClient SYNC_INFO_CLIENT = getClient(SyncInfoClient.class, CoreConfigs.getAthenaHost());
  private static final TestCycleClient TEST_CYCLE_CLIENT = getClient(TestCycleClient.class, CoreConfigs.getAthenaHost(), 10, 900);

  public static ItemTypeDto searchOrCreateItemType(ItemTypeDto itemTypeDto) {
    return Optional.ofNullable(search(itemTypeDto)).orElseGet(() -> {
      ITEM_TYPE_CLIENT.saveOrUpdate(itemTypeDto);
      return search(itemTypeDto);
    });
  }

  public static StatusDto searchOrCreateStatus(StatusDto statusDto) {
    return Optional.ofNullable(search(statusDto)).orElseGet(() -> {
      STATUS_CLIENT.saveOrUpdate(statusDto);
      return search(statusDto);
    });
  }

  public static PriorityDto searchOrCreatePriority(PriorityDto priorityDto) {
    return Optional.ofNullable(search(priorityDto)).orElseGet(() -> {
      PRIORITY_CLIENT.saveOrUpdate(priorityDto);
      return search(priorityDto);
    });
  }

  public static Optional<ItemTypeDto> searchItemType(String keyword) {
    return Optional.ofNullable(ITEM_TYPE_CLIENT.search(keyword));
  }

  public static Optional<StatusDto> searchStatus(String keyword) {
    return Optional.ofNullable(STATUS_CLIENT.search(keyword));
  }

  public static Optional<PriorityDto> searchPriority(String keyword) {
    return Optional.ofNullable(PRIORITY_CLIENT.search(keyword));
  }

  public static Optional<ItemDto> searchItem(String keyword) {
    return Optional.ofNullable(ITEM_CLIENT.search(keyword));
  }

  public static void saveItem(ItemDto itemDto) {
    ITEM_CLIENT.saveOrUpdate(itemDto);
  }

  public static void saveTestCycle(TestCycleDto testCycleDto) {
    TEST_CYCLE_CLIENT.saveOrUpdate(testCycleDto);
  }

  public static TestCycleDto findTestCycleByCode(final String keyword) {
    return TEST_CYCLE_CLIENT.findByCode(keyword);
  }

  public static String getSHA256(final String keyword) {
    return TEST_CYCLE_CLIENT.getSHA256(keyword).getOrDefault("sha", "");
  }

  public static TestCycleDto findLastTestCycleByPattern(final String name, final String versionCode) {
    return TEST_CYCLE_CLIENT.findLastByPattern(name, versionCode);
  }

  public static void saveSyncInfo(final String projectCode, String action, String component, Instant startTime) {
    SYNC_INFO_CLIENT.saveOrUpdate(new SyncInfoDto(projectCode, action, component, startTime, Instant.now()));
  }

  public static Date getLastSyncInfo(final String projectCode, String action, String component) {
    SyncInfoDto search = SYNC_INFO_CLIENT.search(action, component, projectCode);
    return search == null ? null : Date.from(search.getStartTime());
  }

  private static ItemTypeDto search(ItemTypeDto entity) {
    return searchItemType(entity.getCode()).orElseGet(() -> searchItemType(entity.getName()).orElse(null));
  }

  private static StatusDto search(StatusDto entity) {
    return searchStatus(entity.getCode()).orElseGet(() -> searchStatus(entity.getName()).orElse(null));
  }

  private static PriorityDto search(PriorityDto entity) {
    return searchPriority(entity.getCode()).orElseGet(() -> searchPriority(entity.getName()).orElse(null));
  }
}
