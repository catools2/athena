package org.catools.athena.core.common.service;

import lombok.RequiredArgsConstructor;
import org.catools.athena.core.common.entity.UserAlias;
import org.catools.athena.core.common.mapper.CoreMapper;
import org.catools.athena.core.common.repository.UserAliasRepository;
import org.catools.athena.core.model.UserAliasDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

}
