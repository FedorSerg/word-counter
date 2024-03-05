package com.example.demo.repository.impl;

import com.example.demo.entity.PersonEntity;
import com.example.demo.repository.PersonRepository;
import com.example.demo.utils.HibernateSessionUtils;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.NoSuchElementException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * Implementation of {@link PersonRepository} using Hibernate for database operations
 * related to persons.
 *
 * <p>This class provides methods to find a person by login in the database.</p>
 */
@Repository
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {

  private final HibernateSessionUtils<PersonEntity> hibernateSessionUtils;

  /**
   * Find a person by their login in the database.
   *
   * @param login The login of the person to find.
   * @return The person with the specified login.
   * @throws NoSuchElementException If no person with the given login is found.
   * @throws RuntimeException       If an error occurs during database access.
   */
  @Override
  public PersonEntity findByLogin(@NonNull String login) {
    try (Session session = hibernateSessionUtils.getSession()) {
      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<PersonEntity> criteria = cb.createQuery(PersonEntity.class);

      Root<PersonEntity> root = criteria.from(PersonEntity.class);
      criteria.select(root).where(cb.equal(root.get("login"), login));

      return session.createQuery(criteria).getSingleResult();
    } catch (NoResultException e) {
      throw new NoSuchElementException(
          String.format("Login %s was not found in database", login));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
