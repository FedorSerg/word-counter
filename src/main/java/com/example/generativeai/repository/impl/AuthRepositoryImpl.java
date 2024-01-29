package com.example.generativeai.repository.impl;

import com.example.generativeai.entity.AuthorizedUserEntity;
import com.example.generativeai.entity.PersonEntity;
import com.example.generativeai.repository.AuthRepository;
import com.example.generativeai.repository.PersonRepository;
import com.example.generativeai.utils.HibernateSessionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthRepositoryImpl implements AuthRepository {

  private final PersonRepository personRepository;
  private final HibernateSessionUtils<AuthorizedUserEntity> hibernateSessionUtils;

  @Override
  public PersonEntity getAuthorizedPerson() {
    return hibernateSessionUtils.findAllData(AuthorizedUserEntity.class).stream()
        .findFirst().map(AuthorizedUserEntity::getPerson).orElse(null);
  }

  @Override
  public void insertAuthorizedPerson(@NonNull String personLogin) {
    AuthorizedUserEntity newAuthUser = AuthorizedUserEntity.builder()
        .person(personRepository.findByLogin(personLogin))
        .build();
    try (Session session = hibernateSessionUtils.getSession()) {
      this.dropAuthorizedPerson();
      Transaction transaction = session.beginTransaction();
      session.persist(newAuthUser);
      transaction.commit();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void dropAuthorizedPerson() {
    hibernateSessionUtils.truncateTable(AuthorizedUserEntity.class);
  }
}
