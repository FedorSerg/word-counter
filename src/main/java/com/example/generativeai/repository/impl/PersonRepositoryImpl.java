package com.example.generativeai.repository.impl;

import com.example.generativeai.entity.PersonEntity;
import com.example.generativeai.repository.PersonRepository;
import com.example.generativeai.utils.HibernateSessionUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {

  private final HibernateSessionUtils<PersonEntity> hibernateSessionUtils;

  @Override
  public PersonEntity findByLogin(@NonNull String login) {
    try (Session session = hibernateSessionUtils.getSession()) {
      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<PersonEntity> criteria = cb.createQuery(PersonEntity.class);

      Root<PersonEntity> root = criteria.from(PersonEntity.class);
      criteria.select(root).where(cb.equal(root.get("login"), login));

      return session.createQuery(criteria).getSingleResult();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
