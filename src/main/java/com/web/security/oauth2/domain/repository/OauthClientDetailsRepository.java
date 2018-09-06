/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.domain.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.security.oauth2.domain.entity.OauthClientDetailsEntity;

/**
 * The Interface OAuthRepository.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public interface OauthClientDetailsRepository
    extends BaseJpaRepository<OauthClientDetailsEntity, String> {

  /**
   * Update oauth client details archive.
   *
   * @param clientId
   *          the client id
   * @param archived
   *          the archived
   * @return the int
   */
  @Modifying
  @Query("UPDATE OauthClientDetails oau "
      + "SET oau.archived = :archived WHERE oau.clientId = :clientId")
  void updateOauthClientDetailsArchive(@Param(value = "clientId") String clientId,
      @Param(value = "archived") boolean archived);

  /**
   * Find oauth client details.
   *
   * @param clientId
   *          the client id
   * @return the oauth client details
   */
  @Query("SELECT oau FROM OauthClientDetails oau WHERE oau.clientId = :clientId")
  OauthClientDetailsEntity findOauthClientDetails(@Param(value = "clientId") String clientId);
}
