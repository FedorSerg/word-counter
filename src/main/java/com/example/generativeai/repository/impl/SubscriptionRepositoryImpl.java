package com.example.generativeai.repository.impl;

import com.example.generativeai.entity.AuthorizedUserEntity;
import com.example.generativeai.entity.PersonEntity;
import com.example.generativeai.repository.PersonRepository;
import com.example.generativeai.repository.SubscriptionRepository;
import com.example.generativeai.utils.HibernateSessionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

  private final PersonRepository personRepository;
  private final HibernateSessionUtils<AuthorizedUserEntity> hibernateSessionUtils;

  @Override
  public void follow(@NonNull Long authPersonId, @NonNull String personToUnfollowLogin) {
    updateFollow(authPersonId, personToUnfollowLogin,
        "INSERT INTO person_subscriptions(person_id, subscription_id) " +
            "VALUES (%d, %d) ON CONFLICT DO NOTHING");
  }

  @Override
  public void unfollow(@NonNull Long authPersonId, @NonNull String personToUnfollowLogin) {
    updateFollow(authPersonId, personToUnfollowLogin,
        "DELETE FROM person_subscriptions WHERE person_id = %d AND subscription_id = %d");
  }

  private void updateFollow(@NonNull Long authPersonId, @NonNull String personToUnfollowLogin,
                            @NonNull String sql) {
    PersonEntity p = personRepository.findByLogin(personToUnfollowLogin);
    try (Session session = hibernateSessionUtils.getSession()) {
      Transaction transaction = session.beginTransaction();
      session.createNativeMutationQuery(String.format(
          sql, authPersonId, p.getId())).executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
