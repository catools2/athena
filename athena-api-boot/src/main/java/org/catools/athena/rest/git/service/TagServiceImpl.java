package org.catools.athena.rest.git.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.git.model.TagDto;
import org.catools.athena.rest.git.mapper.GitMapper;
import org.catools.athena.rest.git.model.Tag;
import org.catools.athena.rest.git.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
  public Set<TagDto> getAll() {
    return tagRepository.findAll().stream().map(gitMapper::tagToTagDto).collect(Collectors.toSet());
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
