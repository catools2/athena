package org.catools.athena.rest.core.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.core.model.UserAliasDto;
import org.catools.athena.rest.core.entity.UserAlias;
import org.catools.athena.rest.core.mapper.CoreMapper;
import org.catools.athena.rest.core.repository.UserAliasRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAliasServiceImpl implements UserAliasService {

  private final UserAliasRepository userAliasRepository;

  // Mappers
  private final CoreMapper coreMapper;

  @Override
  public UserAliasDto save(UserAliasDto alias) {
    final UserAlias userAliasToSave = coreMapper.userAliasDtoToUserAlias(alias);
    final UserAlias savedUserAlias = userAliasRepository.saveAndFlush(userAliasToSave);
    return coreMapper.userAliasToUserAliasDto(savedUserAlias);
  }

  @Override
  public Optional<UserAliasDto> getById(Long id) {
    return userAliasRepository.findById(id).map(coreMapper::userAliasToUserAliasDto);
  }

  @Override
  public Set<UserAliasDto> getByUserId(Long userId) {
    return userAliasRepository.findByUserId(userId).stream().map(coreMapper::userAliasToUserAliasDto).collect(Collectors.toSet());
  }

  @Override
  public Optional<UserAliasDto> getByAlias(String alias) {
    return userAliasRepository.findByAlias(alias).map(coreMapper::userAliasToUserAliasDto);
  }
}
