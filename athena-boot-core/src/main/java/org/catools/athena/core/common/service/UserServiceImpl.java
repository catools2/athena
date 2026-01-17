package org.catools.athena.core.common.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.catools.athena.core.common.entity.User;
import org.catools.athena.core.common.mapper.CoreMapper;
import org.catools.athena.core.common.repository.UserAliasRepository;
import org.catools.athena.core.common.repository.UserRepository;
import org.catools.athena.core.common.repository.builders.UserDynamicQueryBuilder;
import org.catools.athena.core.entity.UserFilterDto;
import org.catools.athena.core.utils.UserPersistentHelper;
import org.catools.athena.model.core.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserPersistentHelper userPersistentHelper;
  private final UserRepository userRepository;
  private final UserAliasRepository userAliasRepository;
  private final CoreMapper coreMapper;
  private final EntityManager entityManager;

  @Override
  @Transactional(readOnly = true)
  public Page<UserDto> getAll(final Pageable pageable) {
    log.debug("Getting all users with pagination: {}", pageable);
    return userRepository.findAll(pageable).map(coreMapper::userToUserDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<UserDto> getAll(final Pageable pageable, final UserFilterDto filters) {
    log.debug("Getting all users with pagination and filters: {}", filters);

    // Use dynamic query builder with sorting
    UserDynamicQueryBuilder queryBuilder = new UserDynamicQueryBuilder(filters);
    String jpqlQuery = queryBuilder.buildQueryWithSort(pageable);

    TypedQuery<User> query = entityManager.createQuery(jpqlQuery, User.class);

    String countQuery = queryBuilder.buildCountQuery();
    Long total = entityManager.createQuery(countQuery, Long.class).getSingleResult();

    query.setFirstResult((int) pageable.getOffset());
    query.setMaxResults(pageable.getPageSize());

    List<User> users = query.getResultList();
    List<UserDto> body = users.stream().map(coreMapper::userToUserDto).toList();
    return new PageImpl<>(body, pageable, total);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<UserDto> getById(final Long id) {
    log.info("Search for user by id: {}", id);
    final Optional<User> user = userRepository.findById(id);
    return user.map(coreMapper::userToUserDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<UserDto> getByUsername(String username) {
    log.info("Search for user by username: username: {}", username);
    return userRepository.findByUsernameIgnoreCase(username).map(coreMapper::userToUserDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<UserDto> search(final String keyword) {
    log.info("Search for user by username or alias: {}", keyword);
    return userRepository.findByKeywords(Set.of(keyword.toLowerCase())).map(coreMapper::userToUserDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<User> search(final UserDto entity) {
    log.info("Search for user by username or alias: {}", entity);
    Set<String> keywords = new HashSet<>();
    keywords.add(entity.getUsername().toLowerCase());
    entity.getAliases().forEach(a -> keywords.add(a.getAlias().toLowerCase()));
    return userRepository.findByKeywords(keywords);
  }

  @Override
  @Transactional
  public UserDto save(final UserDto entity) {
    log.debug("Saving user: {}", entity);
    User userToSave = coreMapper.userDtoToUser(entity);
    final User savedUser = userPersistentHelper.save(userToSave);
    return coreMapper.userToUserDto(savedUser);
  }

  @Override
  @Transactional
  public UserDto update(final UserDto entity) {
    log.debug("Updating user: {}", entity);
    User userToSave = coreMapper.userDtoToUser(entity);
    final User savedUser = userPersistentHelper.update(userToSave);
    return coreMapper.userToUserDto(savedUser);
  }
}
