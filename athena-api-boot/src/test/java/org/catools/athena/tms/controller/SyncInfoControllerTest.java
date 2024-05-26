package org.catools.athena.tms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.catools.athena.core.common.config.CorePathDefinitions;
import org.catools.athena.tms.common.service.SyncInfoService;
import org.catools.athena.tms.model.SyncInfoDto;
import org.catools.athena.tms.rest.controller.SyncInfoController;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SyncInfoController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SyncInfoControllerTest {
  private static final String SYNC_INFO_PATH = CorePathDefinitions.ROOT_API + SyncInfoController.TMS_SYNC_INFO;

  @Autowired
  MockMvc mvc;

  @MockBean
  SyncInfoService syncInfoService;

  @Test
  void testGetById() throws Exception {
    SyncInfoDto syncInfoDto = new SyncInfoDto();
    syncInfoDto.setId(1L);

    when(syncInfoService.getById(anyLong())).thenReturn(Optional.of(syncInfoDto));

    mvc.perform(MockMvcRequestBuilders.get(SYNC_INFO_PATH + "/1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"id\":1}"));
  }

  @Test
  void testGetByIdNotFound() throws Exception {
    when(syncInfoService.getById(anyLong())).thenReturn(Optional.empty());

    mvc.perform(MockMvcRequestBuilders.get(SYNC_INFO_PATH + "/1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  void testSearch() throws Exception {
    SyncInfoDto syncInfoDto = new SyncInfoDto();
    syncInfoDto.setId(1L);

    when(syncInfoService.search(anyString(), anyString(), anyString())).thenReturn(Optional.of(syncInfoDto));

    mvc.perform(MockMvcRequestBuilders.get(SYNC_INFO_PATH)
            .param("action", "testAction")
            .param("component", "testComponent")
            .param("project", "testProject")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"id\":1}"));
  }

  @Test
  void testSearchNotFound() throws Exception {
    when(syncInfoService.search(anyString(), anyString(), anyString())).thenReturn(Optional.empty());

    mvc.perform(MockMvcRequestBuilders.get(SYNC_INFO_PATH)
            .param("action", "testAction")
            .param("component", "testComponent")
            .param("project", "testProject")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  void testSaveOrUpdate() throws Exception {
    SyncInfoDto syncInfoDto = new SyncInfoDto();
    syncInfoDto.setProject("PX");
    syncInfoDto.setAction("Action");
    syncInfoDto.setComponent("component");
    syncInfoDto.setEndTime(Instant.now());
    syncInfoDto.setStartTime(Instant.now().minusSeconds(100));
    syncInfoDto.setId(1L);

    when(syncInfoService.saveOrUpdate(any(SyncInfoDto.class))).thenReturn(syncInfoDto);

    mvc.perform(MockMvcRequestBuilders.post(SYNC_INFO_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().findAndRegisterModules().writeValueAsString(syncInfoDto)))
        .andExpect(status().isCreated())
        .andExpect(content().string(""));
  }
}