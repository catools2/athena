package org.catools.etl.tms.dao;

import lombok.extern.slf4j.Slf4j;
import org.catools.common.collections.interfaces.CCollection;
import org.catools.common.utils.CRetry;
import org.catools.etl.tms.configs.CEtlConfigs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Slf4j
public class CEtlBaseDao {
  private static EntityManagerFactory entityManagerFactory = null;

  static {
    Runtime.getRuntime().addShutdownHook(new Thread(CEtlBaseDao::closeEntityManagerFactory));
  }

  public static <T> List<T> find(Class<T> entityClass) {
    return getTransactionResult(session -> {
      CriteriaBuilder builder = session.getCriteriaBuilder();
      CriteriaQuery<T> criteria = builder.createQuery(entityClass);
      criteria.from(entityClass);
      return session.createQuery(criteria).getResultList();
    });
  }

  public static <T> void persist(T record) {
    doTransaction(session -> {
      session.persist(record);
      return true;
    });
  }

  public static <T> void persist(CCollection<T, Collection<T>> records) {
    log.trace("Performing bulk persist on {} records.", records.size());
    bulk(records, EntityManager::persist);
  }

  public static <T> T find(Class<T> entityClass, Object primaryKey) {
    return getTransactionResult(session -> session.find(entityClass, primaryKey));
  }

  public static <T> T merge(T record) {
    return doTransaction(session -> session.merge(record));
  }

  public static <T> void merge(CCollection<T, Collection<T>> records) {
    log.trace("Performing bulk merge on {} records.", records.size());
    bulk(records, EntityManager::merge);
  }

  public static <T> void remove(CCollection<T, Collection<T>> records) {
    log.trace("Performing bulk remove on {} records.", records.size());
    bulk(records, (session, record) -> session.remove(session.merge(record)));
  }

  protected static <T> void bulk(CCollection<T, Collection<T>> records, BiConsumer<EntityManager, T> action) {
    int partitionSize = CEtlConfigs.getEtlBulkTransactionPartitionSize();
    records.partition(partitionSize).forEach(partition -> {
      doTransaction(
          session -> {
            String currentThreadName = Thread.currentThread().getName();
            log.trace("thread {} started processing {} records.", currentThreadName, partition.size());
            for (T record : partition) {
              action.accept(session, record);
            }
            flashAndClear(session);
            log.trace("thread {} finished processing {} records.", currentThreadName, partition.size());
            return true;
          });
    });
  }

  protected static <T> T getTransactionResult(Function<EntityManager, T> action) {
    EntityManager em = getEntityManager();
    EntityTransaction tx = null;
    try {
      tx = em.getTransaction();
      tx.begin();
      T result = action.apply(em);
      tx.commit();
      return result;
    } catch (Exception e) {
      tryRollback(tx, e);
      throw e;
    } finally {
      em.close();
    }
  }

  protected static <T> T doTransaction(Function<EntityManager, T> action) {
    EntityManager em = getEntityManager();
    EntityTransaction tx = null;
    try {
      tx = em.getTransaction();
      tx.begin();
      T t = action.apply(em);
      tx.commit();
      return t;
    } catch (Exception e) {
      tryRollback(tx, e);
      throw e;
    } finally {
      em.close();
    }
  }

  @SafeVarargs
  protected static void doTransactions(Function<EntityManager, Serializable>... actions) {
    EntityManager em = getEntityManager();
    EntityTransaction tx = null;
    try {
      tx = em.getTransaction();
      tx.begin();
      for (Function<EntityManager, Serializable> action : actions) {
        action.apply(em);
      }
      tx.commit();
    } catch (Exception e) {
      tryRollback(tx, e);
      throw e;
    } finally {
      em.close();
    }
  }

  private static void tryRollback(EntityTransaction tx, Exception e) {
    if (tx != null) {
      try {
        tx.rollback();
      } catch (Exception e1) {
        log.error("Failed To Perform Rollback.", e);
      }
    }
  }

  protected static synchronized EntityManagerFactory getEntityManagerFactory() {
    if (entityManagerFactory == null) {
      entityManagerFactory = CRetry.retry(idx -> {
        log.debug("Attempt {} to connect to create etl entity manager", idx + 1);
        return Persistence.createEntityManagerFactory("CEtlPersistence");
      }, 10, 10);
    }
    return entityManagerFactory;
  }

  protected static synchronized void closeEntityManagerFactory() {
    if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
      entityManagerFactory.close();
    }
  }

  private static void flashAndClear(EntityManager session) {
    if (session.isOpen()) {
      session.flush();
      session.clear();
    }
  }

  protected static EntityManager getEntityManager() {
    return CRetry.retry(idx -> getEntityManagerFactory().createEntityManager(), 10, 10);
  }
}
