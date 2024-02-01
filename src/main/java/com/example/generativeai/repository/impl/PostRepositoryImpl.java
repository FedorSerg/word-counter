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

/**
 * Implementation of {@link PostRepository} using Hibernate for database operations
 * related to posts.
 *
 * <p>This class provides methods for retrieving posts, finding posts by subscriptions,
 * saving new posts, and updating likes on posts.</p>
 */
@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

  private final HibernateSessionUtils<PostEntity> hibernateSessionUtils;

  /**
   * Retrieve all posts from the database.
   *
   * @return A list of all posts.
   */
  @Override
  public List<PostEntity> findAll() {
    return hibernateSessionUtils.findAllData(PostEntity.class);
  }

  /**
   * Retrieve posts from the database based on the subscriptions of the authenticated person.
   *
   * @param authPersonId The authenticated person ID.
   * @return A list of posts from subscribed authors.
   * @throws RuntimeException If an error occurs during database access.
   */
  @Override
  public List<PostEntity> findAllBySubscriptions(@NonNull Long authPersonId) {
    try (Session session = hibernateSessionUtils.getSession()) {
      return session.createNativeQuery(String.format(
              "SELECT post.* FROM post "
                  + "JOIN person author ON post.author_id = author.id "
                  + "JOIN person_subscriptions ps ON author.id = ps.subscription_id "
                  + "WHERE ps.person_id = %d", authPersonId),
          PostEntity.class).getResultList();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Save a new post in the database.
   *
   * @param post The post to be saved.
   * @return The saved post.
   */
  @Override
  public PostEntity save(@NonNull PostEntity post) {
    Object id = hibernateSessionUtils.save(post);
    return hibernateSessionUtils.findDataById(PostEntity.class, id);
  }

  /**
   * Update the like status on a post in the database.
   *
   * @param authPersonId The authenticated person ID.
   * @param postId       The ID of the post.
   * @return The updated post.
   * @throws RuntimeException If an error occurs during database access.
   */
  @Override
  public PostEntity updateLikeOnPost(@NonNull Long authPersonId, @NonNull Long postId) {
    PostEntity post = hibernateSessionUtils.findDataById(PostEntity.class, postId);
    String sql = post.getLikedPersons().stream()
        .map(PersonEntity::getId).toList().contains(authPersonId)
        ? "DELETE FROM post_likes WHERE post_id = %d AND person_id = %d"
        : "INSERT INTO post_likes(post_id, person_id) VALUES (%d, %d) ON CONFLICT DO NOTHING";
    updateLike(authPersonId, postId, sql);
    return hibernateSessionUtils.findDataById(PostEntity.class, postId);
  }

  private void updateLike(@NonNull Long authPersonId, @NonNull Long postId,
                          @NonNull String sql) {
    try (Session session = hibernateSessionUtils.getSession()) {
      Transaction transaction = session.beginTransaction();
      session.createNativeMutationQuery(String.format(sql,
          postId, authPersonId)).executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
