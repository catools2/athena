package org.catools.athena.rest.tms.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.core.model.MetadataDto;
import org.catools.athena.rest.tms.entity.ItemMetadata;
import org.catools.athena.rest.tms.mapper.TmsMapper;
import org.catools.athena.rest.tms.repository.ItemMetadataRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemMetadataServiceImpl implements ItemMetadataService {
    private final ItemMetadataRepository itemMetaDataRepository;
    private final TmsMapper tmsMapper;

    @Override
    public MetadataDto save(MetadataDto record) {
        final ItemMetadata recordToSave = tmsMapper.metadataDtoToItemMetadata(record);
        final ItemMetadata savedRecord = itemMetaDataRepository.saveAndFlush(recordToSave);
        return tmsMapper.itemMetadataToMetadataDto(savedRecord);
    }

    @Override
    public Set<MetadataDto> getAll() {
        return itemMetaDataRepository.findAll().stream().map(tmsMapper::itemMetadataToMetadataDto).collect(Collectors.toSet());
    }

    @Override
    public Optional<MetadataDto> getById(Long id) {
        return itemMetaDataRepository.findById(id).map(tmsMapper::itemMetadataToMetadataDto);
    }
}
