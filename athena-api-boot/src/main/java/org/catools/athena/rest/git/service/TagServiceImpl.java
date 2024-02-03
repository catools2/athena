package org.catools.athena.rest.git.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.git.model.TagDto;
import org.catools.athena.rest.git.mapper.GitMapper;
import org.catools.athena.rest.git.model.Tag;
import org.catools.athena.rest.git.repository.TagRepository;
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
