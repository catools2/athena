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

  // ...existing code...

  /**
   * Execute dynamic JPQL query with pagination (deprecated - use query builder methods instead)
   */
  private Page<UserDto> executeQuery(String jpqlQuery, Pageable pageable) {
    // Apply sorting from Pageable
    String queryWithSort = applySort(jpqlQuery, pageable);

    TypedQuery<User> query = entityManager.createQuery(queryWithSort, User.class);

    // Build safe count query by replacing only the main SELECT clause
    String countQuery = buildCountQuery(jpqlQuery);
    Long total = entityManager.createQuery(countQuery, Long.class).getSingleResult();

    // Apply pagination
    query.setFirstResult((int) pageable.getOffset());
    query.setMaxResults(pageable.getPageSize());

    List<User> users = query.getResultList();
    List<UserDto> body = users.stream().map(coreMapper::userToUserDto).toList();
    return new PageImpl<>(body, pageable, total);
  }

  /**
   * Convert a JPQL select query (with alias) into a COUNT query, preserving WHERE/JOIN clauses.
   * Example: "SELECT u FROM User u WHERE ..." -> "SELECT COUNT(u) FROM User u WHERE ..."
   */
  private String buildCountQuery(String jpqlQuery) {
    // Regex captures: SELECT <alias> FROM <entity> <alias>
    // Then replace SELECT <alias> with SELECT COUNT(<alias>)
    return jpqlQuery.replaceFirst("(?i)^\\s*SELECT\\s+([a-zA-Z][a-zA-Z0-9_]*)\\s+FROM\\s+([a-zA-Z][a-zA-Z0-9_.]*)\\s+\\1", "SELECT COUNT($1) FROM $2 $1");
  }

  /**
   * Apply ORDER BY clause from Pageable's Sort to JPQL query (deprecated - use query builder instead)
   */
  private String applySort(String jpqlQuery, Pageable pageable) {
    if (pageable.getSort().isUnsorted()) {
      return jpqlQuery;
    }

    StringBuilder orderByClause = new StringBuilder(" ORDER BY ");
    pageable.getSort().forEach(order -> {
      orderByClause.append("lower(u.")
          .append(order.getProperty())
          .append(") ")
          .append(order.getDirection().name())
          .append(", ");
    });
    orderByClause.append("u.id ASC");
    return jpqlQuery + orderByClause;
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