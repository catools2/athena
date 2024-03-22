package org.catools.etl.k8s.dao;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.utils.CRetry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.function.Function;

@Slf4j
public class CEtlKubeBaseDao {
  private static EntityManagerFactory entityManagerFactory = null;

  static {
    Runtime.getRuntime().addShutdownHook(new Thread(CEtlKubeBaseDao::closeEntityManagerFactory));
  }

  public static <T> T find(Class<T> entityClass, Object primaryKey) {
    return doTransaction(session -> session.find(entityClass, primaryKey));
  }

  public static void remove(Object record) {
    doTransaction(session -> {
      session.remove(record);
      return true;
    });
  }


  public static <T> T merge(T record) {
    return doTransaction(session -> session.merge(record));
  }

  public synchronized static <T> T doTransaction(Function<EntityManager, T> action) {
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
      if (session.isJoinedToTransaction())
        session.flush();
      session.close();
    }
  }

  protected static synchronized EntityManagerFactory getEntityManagerFactory() {
    if (entityManagerFactory == null) {
      entityManagerFactory = CRetry.retry(idx -> {
        log.debug("Attempt {} to connect to create kube entity manager", idx + 1);
        return Persistence.createEntityManagerFactory("CKubePersistence");
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
