package com.example.generativeai.repository.impl;

import com.example.generativeai.entity.AuthorizedUserEntity;
import com.example.generativeai.entity.PersonEntity;
import com.example.generativeai.repository.AuthRepository;
import com.example.generativeai.repository.PersonRepository;
import com.example.generativeai.utils.HibernateSessionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Implementation of {@link AuthRepository} using Hibernate for database operations related to
 * authorized users.
 *
 * <p>This class provides methods to retrieve, insert, and drop authorized user information
 * in the database.</p>
 */
@Repository
@RequiredArgsConstructor
public class AuthRepositoryImpl implements AuthRepository {

  private final PersonRepository personRepository;
  private final HibernateSessionUtils<AuthorizedUserEntity> hibernateSessionUtils;

  /**
   * Retrieve the authorized person from the database.
   *
   * @return The authorized person or {@code null} if not found.
   * @throws RuntimeException If an error occurs during database access.
   */
  @Override
  public PersonEntity getAuthorizedPerson() {
    return hibernateSessionUtils.findAllData(AuthorizedUserEntity.class).stream()
        .findFirst().map(AuthorizedUserEntity::getPerson).orElse(null);
  }

  /**
   * Insert a new authorized person into the database.
   *
   * <p>The existing authorized person, if any, will be dropped before inserting the new one.</p>
   *
   * @param personLogin The login of the person to authorize.
   * @throws RuntimeException If an error occurs during database access.
   */
  @Override
  public void insertAuthorizedPerson(@NonNull String personLogin) {
    AuthorizedUserEntity newAuthUser = AuthorizedUserEntity.builder()
        .person(personRepository.findByLogin(personLogin))
        .build();
    this.dropAuthorizedPerson();
    hibernateSessionUtils.save(newAuthUser);
  }

  /**
   * Drop the authorized person from the database.
   *
   * <p>This operation removes all records related to authorized users.</p>
   *
   * @throws RuntimeException If an error occurs during database access.
   */
  @Override
  public void dropAuthorizedPerson() {
    hibernateSessionUtils.truncateTable(AuthorizedUserEntity.class);
  }
}
