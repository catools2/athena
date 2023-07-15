package org.catools.pipeline.dao;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.utils.CRetry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.function.Function;

@Slf4j
public class CPipelineBaseDao {
  private static EntityManagerFactory entityManagerFactory = null;

  static {
    Runtime.getRuntime().addShutdownHook(new Thread(CPipelineBaseDao::closeEntityManagerFactory));
  }

  public static <T> T find(Class<T> entityClass, Object primaryKey) {
    return getTransactionResult(session -> session.find(entityClass, primaryKey));
  }

  public static <T> T merge(T record) {
    return doTransaction(session -> session.merge(record));
  }

  protected static <T> T getTransactionResult(Function<EntityManager, T> action) {
    EntityManager session = getEntityManager();
    EntityTransaction tx = null;
    try {
      tx = session.getTransaction();
      tx.begin();
      T result = action.apply(session);
      tx.commit();
      return result;
    } catch (Exception e) {
      if (tx != null) {
        tx.rollback();
      }
      log.error("Failed To Perform Transaction.", e);
      throw e;
    } finally {
      session.close();
    }
  }

  protected static <T> T doTransaction(Function<EntityManager, T> action) {
    EntityManager session = getEntityManager();
    EntityTransaction tx = null;
    try {
      tx = session.getTransaction();
      tx.begin();
      T result = action.apply(session);
      tx.commit();
      return result;
    } catch (Exception e) {
      log.error("Failed To Perform Transaction.", e);
      if (tx != null) {
        tx.rollback();
      }
      throw e;
    } finally {
      session.close();
    }
  }

  protected static synchronized EntityManagerFactory getEntityManagerFactory() {
    if (entityManagerFactory == null) {
      entityManagerFactory = CRetry.retry(idx -> {
        log.debug("Attempt {} to connect to create pipeline entity manager", idx + 1);
        return Persistence.createEntityManagerFactory("CPipelinePersistence");
      }, 10, 10);
    }
    return entityManagerFactory;
  }

  protected static synchronized void closeEntityManagerFactory() {
    if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
      entityManagerFactory.close();
    }
  }

  protected static EntityManager getEntityManager() {
    return CRetry.retry(idx -> getEntityManagerFactory().createEntityManager(), 10, 10);
  }
}
