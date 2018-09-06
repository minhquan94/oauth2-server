/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.domain.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;

/**
 * The Class BaseJpaRepositoryImpl.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 * @param <T>
 *          the generic type
 * @param <I>
 *          the generic type
 */
public class BaseJpaRepositoryImpl<T, I extends Serializable>
    extends SimpleJpaRepository<T, I> implements BaseJpaRepository<T, I> {

  /** The Constant DELETE_BATCH_QUERY_STRING. */
  public static final String DELETE_BATCH_QUERY_STRING = "DELETE FROM %s x WHERE x.id IN (?1)";

  /** The Constant QUERY_LIST_STRING. */
  public static final String QUERY_LIST_STRING = "SELECT x FROM %s x WHERE x.id IN (?1)";

  /** The entity information. */
  private final JpaEntityInformation<T, ? > entityInformation;

  /** The entity manager. */
  private final EntityManager entityManager;

  /**
   * Instantiates a new base JPA repository implementation.
   *
   * @param entityInformation
   *          the entity information
   * @param entityManager
   *          the entity manager
   */
  public BaseJpaRepositoryImpl(final JpaEntityInformation<T, ? > entityInformation,
      final EntityManager entityManager) {
    super(entityInformation, entityManager);

    // Keep the EntityManager around to used from the newly introduced methods.
    this.entityInformation = entityInformation;
    this.entityManager = entityManager;
  }

  /**
   * {@inheritDoc}
   *
   * @see com.gcs.virdict.data.jpa.repositories.BaseJpaRepository#deleteBatch(java.lang.Iterable)
   */
  @Override
  public int deleteBatch(final Iterable<I> ids) {
    Assert.notNull(ids, "The given Iterable of ids not be null!");

    if (!ids.iterator().hasNext()) {
      return 0;
    }

    // This is JPA query, it is secure usage
    // http://software-security.sans.org/developer-how-to/fix-sql-injection-in-java-persistence-api-jpa
    final Query query = entityManager.createQuery( // NOSONAR
        String.format(DELETE_BATCH_QUERY_STRING, entityInformation.getEntityName()));
    query.setParameter(1, ids);

    return query.executeUpdate();
  }

  /**
   * {@inheritDoc}
   *
   * @see com.gcs.virdict.data.jpa.repositories.BaseJpaRepository#refresh(java.lang.Object)
   */
  @Override
  public <S extends T> void refresh(final S entity) {
    entityManager.refresh(entity);
  }

  /**
   * {@inheritDoc}
   *
   * @see com.gcs.virdict.data.jpa.repositories.BaseJpaRepository#findAll(java.util.List)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> findAll(List<I> ids) {
    Assert.notNull(ids, "The given List of ids not be null!");

    if (!ids.iterator().hasNext()) {
      return new ArrayList<>();
    }

    // This is JPA query, it is secure usage
    // http://software-security.sans.org/developer-how-to/fix-sql-injection-in-java-persistence-api-jpa
    final Query query = entityManager
        .createQuery(String.format(QUERY_LIST_STRING, entityInformation.getEntityName())); // NOSONAR
    query.setParameter(1, ids);

    return query.getResultList();
  }

  /**
   * {@inheritDoc}
   *
   * @see com.gcs.virdict.data.jpa.repositories.BaseJpaRepository#saveBatch(java.util.Collection)
   */
  @Override
  public List<T> saveBatch(Collection<T> entities) {
    final List<T> savedEntities = new ArrayList<>(entities.size());
    for (final T t : entities) {
      entityManager.persist(t);
      savedEntities.add(t);
    }
    // Flush a batch of inserts and release memory.
    entityManager.flush();
    entityManager.clear();
    return savedEntities;
  }
}
