package org.catools.athena.rest.git.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.git.model.BranchDto;
import org.catools.athena.rest.git.mapper.GitMapper;
import org.catools.athena.rest.git.model.Branch;
import org.catools.athena.rest.git.repository.BranchRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
  private final BranchRepository branchRepository;
  private final GitMapper gitMapper;

  @Override
  public BranchDto save(BranchDto entity) {
    final Branch entityToSave = gitMapper.branchDtoToBranch(entity);
    final Branch savedEntity = branchRepository.saveAndFlush(entityToSave);
    return gitMapper.branchToBranchDto(savedEntity);
  }

  @Override
  public Set<BranchDto> getAll() {
    return branchRepository.findAll().stream().map(gitMapper::branchToBranchDto).collect(Collectors.toSet());
  }

  @Override
  public Optional<BranchDto> getById(Long id) {
    return branchRepository.findById(id).map(gitMapper::branchToBranchDto);
  }

  @Override
  public Optional<BranchDto> search(String hash) {
    return branchRepository.findByHash(hash).map(gitMapper::branchToBranchDto);
  }
}
