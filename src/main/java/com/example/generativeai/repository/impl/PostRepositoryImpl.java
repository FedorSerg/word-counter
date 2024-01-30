package com.example.generativeai.repository.impl;

import com.example.generativeai.entity.PersonEntity;
import com.example.generativeai.entity.PostEntity;
import com.example.generativeai.repository.PostRepository;
import com.example.generativeai.utils.HibernateSessionUtils;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

  private final HibernateSessionUtils<PostEntity> hibernateSessionUtils;

  @Override
  public List<PostEntity> findAll() {
    return hibernateSessionUtils.findAllData(PostEntity.class);
  }

  @Override
  public List<PostEntity> findAllBySubscriptions(@NonNull PersonEntity authPerson) {
    try (Session session = hibernateSessionUtils.getSession()) {
      return session.createNativeQuery(String.format(
              "SELECT post.* FROM post " +
                  "JOIN person author ON post.author_id = author.id " +
                  "JOIN person_subscriptions ps ON author.id = ps.subscription_id " +
                  "WHERE ps.person_id = %d", authPerson.getId()),
          PostEntity.class).getResultList();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public PostEntity save(@NonNull PostEntity post) {
    Object id = hibernateSessionUtils.save(post);
    return hibernateSessionUtils.findDataById(PostEntity.class, id);
  }

  @Override
  public PostEntity updateLikeOnPost(@NonNull PersonEntity authPerson, @NonNull Long postId) {
    PostEntity post = hibernateSessionUtils.findDataById(PostEntity.class, postId);
    String sql = post.getLikedPersons().contains(authPerson)
        ? "DELETE FROM post_likes WHERE post_id = %d AND person_id = %d"
        : "INSERT INTO post_likes(post_id, person_id) VALUES (%d, %d) ON CONFLICT DO NOTHING";
    updateLike(authPerson, postId, sql);
    return hibernateSessionUtils.findDataById(PostEntity.class, postId);
  }

  private void updateLike(@NonNull PersonEntity authPerson, @NonNull Long postId, @NonNull String sql) {
    try (Session session = hibernateSessionUtils.getSession()) {
      Transaction transaction = session.beginTransaction();
      session.createNativeMutationQuery(String.format(sql,
          postId, authPerson.getId())).executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
