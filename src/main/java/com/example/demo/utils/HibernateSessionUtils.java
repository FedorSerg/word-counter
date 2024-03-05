package com.example.demo.utils;

import com.example.demo.entity.AuthorizedUserEntity;
import com.example.demo.entity.PersonEntity;
import com.example.demo.entity.PostEntity;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Utility class for managing Hibernate sessions and database operations.
 * This class provides methods to obtain Hibernate sessions, execute common database operations,
 * and handle resource management.
 *
 * @param <T> The type of entity for which the session utilities are provided.
 */
@Component
@RequiredArgsConstructor
public class HibernateSessionUtils<T> {

  @Value("${spring.datasource.url}")
  private String datasourceUrl;

  @Value("${spring.datasource.username}")
  private String datasourceUsername;

  @Value("${spring.datasource.password}")
  private String datasourcePassword;

  @Value("${project.db.show-hibernate-scripts}")
  private String showHibernateScripts;

  @Value("${project.db.driver-class}")
  private String dbDriverClass;

  private SessionFactory sessionFactory;

  /**
   * Obtain a Hibernate session.
   * If the session factory is not initialized, it is created using configuration properties and
   * annotated entity classes. The configuration includes connection details and Hibernate settings.
   *
   * @return A Hibernate session.
   */
  public Session getSession() {
    if (sessionFactory == null) {
      try {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.url", datasourceUrl);
        configuration.setProperty("hibernate.connection.username", datasourceUsername);
        configuration.setProperty("hibernate.connection.password", datasourcePassword);

        configuration.setProperty("hibernate.connection.driver_class", dbDriverClass);
        configuration.setProperty("hibernate.show_sql", showHibernateScripts);

        configuration.addAnnotatedClass(PersonEntity.class);
        configuration.addAnnotatedClass(AuthorizedUserEntity.class);
        configuration.addAnnotatedClass(PostEntity.class);

        sessionFactory = configuration.buildSessionFactory();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return sessionFactory.openSession();
  }

  /**
   * Retrieve all instances of a specific entity type from the database.
   *
   * @param type The entity class to query.
   * @return A list of entities retrieved from the database.
   * @throws RuntimeException If an error occurs during database access.
   */
  public List<T> findAllData(@NonNull Class<T> type) {
    try (Session session = this.getSession()) {
      CriteriaQuery<T> criteria = session.getCriteriaBuilder().createQuery(type);
      criteria.from(type);
      return session.createQuery(criteria).getResultList();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Retrieve an entity by its identifier.
   *
   * @param type The entity class.
   * @param id   The identifier of the entity to retrieve.
   * @return The entity with the specified identifier, or {@code null} if not found.
   * @throws RuntimeException If an error occurs during database access.
   */
  public T findDataById(@NonNull Class<T> type, @NonNull Object id) {
    try (Session session = this.getSession()) {
      return session.get(type, id);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Truncate a table by deleting all records.
   *
   * @param type The entity class representing the table to truncate.
   * @throws RuntimeException If an error occurs during database access.
   */
  public void truncateTable(@NonNull Class<T> type) {
    try (Session session = this.getSession()) {
      CriteriaDelete<T> criteria = session.getCriteriaBuilder().createCriteriaDelete(type);
      Transaction transaction = session.beginTransaction();
      session.createMutationQuery(criteria).executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Save an entity to the database.
   *
   * @param entity The entity to save.
   * @return The identifier of the saved entity.
   * @throws RuntimeException If an error occurs during database access.
   */
  public Object save(T entity) {
    Object id;
    try (Session session = this.getSession()) {
      Transaction transaction = session.beginTransaction();
      id = session.save(entity); // deprecated, but 'session.persist(entity)' does not return ID
      transaction.commit();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return id;
  }
}
