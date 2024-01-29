package com.example.generativeai.utils;

import com.example.generativeai.entity.AuthorizedUserEntity;
import com.example.generativeai.entity.PersonEntity;
import com.example.generativeai.entity.PostEntity;
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

  public List<T> findAllData(@NonNull Class<T> type) {
    try (Session session = this.getSession()) {
      CriteriaQuery<T> criteria = session.getCriteriaBuilder().createQuery(type);
      criteria.from(type);
      return session.createQuery(criteria).getResultList();

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public T findDataById(@NonNull Class<T> type, Object id) {
    try (Session session = this.getSession()) {
      return session.get(type, id);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

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

}