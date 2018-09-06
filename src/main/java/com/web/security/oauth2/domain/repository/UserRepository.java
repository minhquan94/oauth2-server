/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.domain.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.security.oauth2.domain.entity.UserEntity;

/**
 * The Interface UserRepository.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public interface UserRepository extends BaseJpaRepository<UserEntity, Long> {

  /**
   * Find by username.
   *
   * @param username
   *          the username
   * @return the user entity
   */
  @Query("SELECT u FROM User u where u.username = :username")
  UserEntity findByUsername(@Param(value = "username") String username);

  /**
   * Find by guid.
   *
   * @param guid
   *          the guid
   * @return the user entity
   */
  @Query("SELECT u FROM User u where u.guid = :guid")
  UserEntity findByGuid(@Param(value = "guid") String guid);
}
