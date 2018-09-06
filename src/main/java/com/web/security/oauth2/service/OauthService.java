/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.service;

import java.util.List;

import com.web.security.oauth2.domain.entity.OauthClientDetailsEntity;
import com.web.security.oauth2.dto.OauthClientDetailsDto;

/**
 * The Interface OauthService.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
public interface OauthService {

  /**
   * Load oauth client details.
   *
   * @param clientId
   *          the client id
   * @return the oauth client details
   */
  OauthClientDetailsEntity loadOauthClientDetails(String clientId);

  /**
   * Load all oauth client details dtos.
   *
   * @return the list
   */
  List<OauthClientDetailsDto> loadAllOauthClientDetailsDtos();

  /**
   * Archive oauth client details.
   *
   * @param clientId
   *          the client id
   */
  void archiveOauthClientDetails(String clientId);

  /**
   * Load oauth client details dto.
   *
   * @param clientId
   *          the client id
   * @return the oauth client details dto
   */
  OauthClientDetailsDto loadOauthClientDetailsDto(String clientId);

  /**
   * Register client details.
   *
   * @param formDto
   *          the form dto
   */
  void registerClientDetails(OauthClientDetailsDto formDto);
}
