package org.catools.athena.tms.controller;

import org.catools.athena.core.common.config.CorePathDefinitions;
import org.catools.athena.tms.common.service.StatusService;
import org.catools.athena.tms.model.StatusDto;
import org.catools.athena.tms.rest.controller.StatusController;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatusController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StatusControllerTest {
  private static final String STATUS_PATH = CorePathDefinitions.ROOT_API + StatusController.TMS_STATUS;

  @Autowired
  MockMvc mockMvc;

  @MockBean
  StatusService statusService;

  @Test
  void testSaveOrUpdate() throws Exception {
    StatusDto statusDto = new StatusDto();
    statusDto.setId(1L);

    when(statusService.saveOrUpdate(any(StatusDto.class))).thenReturn(statusDto);

    mockMvc.perform(MockMvcRequestBuilders.post(STATUS_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"code\":\"testCode\",\"name\":\"testName\"}"))
        .andExpect(status().isCreated())
        .andExpect(content().string(""));
  }

  @Test
  void testGetById() throws Exception {
    StatusDto statusDto = new StatusDto();
    statusDto.setId(1L);

    when(statusService.getById(anyLong())).thenReturn(Optional.of(statusDto));

    mockMvc.perform(MockMvcRequestBuilders.get(STATUS_PATH + "/1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"id\":1}"));
  }

  @Test
  void testGetByIdNotFound() throws Exception {
    when(statusService.getById(anyLong())).thenReturn(Optional.empty());

    mockMvc.perform(MockMvcRequestBuilders.get(STATUS_PATH + "/1")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  void testSearch() throws Exception {
    StatusDto statusDto = new StatusDto();
    statusDto.setId(1L);

    when(statusService.search(anyString())).thenReturn(Optional.of(statusDto));

    mockMvc.perform(MockMvcRequestBuilders.get(STATUS_PATH)
            .param("keyword", "testKeyword")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json("{\"id\":1}"));
  }

  @Test
  void testSearchNotFound() throws Exception {
    when(statusService.search(anyString())).thenReturn(Optional.empty());

    mockMvc.perform(MockMvcRequestBuilders.get(STATUS_PATH)
            .param("keyword", "testKeyword")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }
}
