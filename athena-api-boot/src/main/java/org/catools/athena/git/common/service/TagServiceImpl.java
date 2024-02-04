package org.catools.athena.git.common.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.git.common.mapper.GitMapper;
import org.catools.athena.git.common.model.Tag;
import org.catools.athena.git.common.repository.TagRepository;
import org.catools.athena.git.model.TagDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
  private final TagRepository tagRepository;
  private final GitMapper gitMapper;

  @Override
  public TagDto save(TagDto entity) {
    final Tag entityToSave = gitMapper.tagDtoToTag(entity);
    final Tag savedEntity = tagRepository.saveAndFlush(entityToSave);
    return gitMapper.tagToTagDto(savedEntity);
  }

  @Override
  public Optional<TagDto> getById(Long id) {
    return tagRepository.findById(id).map(gitMapper::tagToTagDto);
  }

  @Override
  public Optional<TagDto> search(String keyword) {
    return tagRepository.findByNameOrHash(keyword, keyword).map(gitMapper::tagToTagDto);
  }
}
