package org.catools.athena.rest.tms.mapper;

import lombok.RequiredArgsConstructor;
import org.catools.athena.rest.tms.entity.*;
import org.catools.athena.rest.tms.repository.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TmsMapperServiceImpl implements TmsMapperService {

    private final ItemRepository itemRepository;
    private final ItemTypeRepository itemTypeRepository;
    private final StatusRepository statusRepository;
    private final PriorityRepository priorityRepository;
    private final TestCycleRepository testCycleRepository;

    @Override
    public Item getItemByCode(String code) {
        return itemRepository.findByCode(code).orElse(null);
    }

    @Override
    public ItemType getItemTypeByCode(String code) {
        return itemTypeRepository.findByCode(code).orElse(null);
    }

    @Override
    public Status getStatusByCode(String code) {
        return statusRepository.findByCode(code).orElse(null);
    }

    @Override
    public Priority getPriorityByCode(String code) {
        return priorityRepository.findByCode(code).orElse(null);
    }

    @Override
    public TestCycle getTestCycleByCode(String code) {
        return testCycleRepository.findByCode(code).orElse(null);
    }
}
