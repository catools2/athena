package org.catools.athena.tms.common.service;

import org.catools.athena.model.tms.TestCycleDto;
import org.catools.athena.tms.common.entity.TestCycle;
import org.catools.athena.tms.common.mapper.TmsMapper;
import org.catools.athena.tms.common.mapper.TmsMapperService;
import org.catools.athena.tms.common.repository.TestCycleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestCycleServiceImplTest {

  @Mock
  private TestCycleRepository testCycleRepository;

  @Mock
  private TmsMapperService tmsMapperService;

  @Mock
  private TmsMapper tmsMapper;

  @InjectMocks
  private TestCycleServiceImpl testCycleService;

  @Test
  void saveOrUpdate_shouldReturnSavedIdWithoutMappingExecutionGraph() {
    TestCycleDto input = new TestCycleDto().setCode("DEMO-C3026");
    TestCycle mappedCycle = new TestCycle().setCode("DEMO-C3026");
    TestCycle savedCycle = new TestCycle().setId(42L).setCode("DEMO-C3026");

    when(tmsMapper.testCycleDtoToTestCycle(input)).thenReturn(mappedCycle);
    when(testCycleRepository.findByCodeWithRelations(input.getCode())).thenReturn(Optional.empty());
    when(testCycleRepository.saveAndFlush(mappedCycle)).thenReturn(savedCycle);

    TestCycleDto result = testCycleService.saveOrUpdate(input);

    assertThat(result.getId()).isEqualTo(42L);
    assertThat(result.getTestExecutions()).isEmpty();
    verify(tmsMapper, never()).testCycleToTestCycleDto(savedCycle);
  }
}
