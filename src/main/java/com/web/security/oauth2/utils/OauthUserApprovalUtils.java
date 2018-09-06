/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;

import com.web.security.oauth2.domain.entity.OauthClientDetailsEntity;
import com.web.security.oauth2.service.OauthService;

/**
 * The Class OauthUserApprovalUtils.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
public class OauthUserApprovalUtils extends TokenStoreUserApprovalHandler {

  /** The oauth service. */
  private OauthService oauthService;

  /**
   * Instantiates a new oauth user approval handler.
   */
  public OauthUserApprovalUtils() {
    // do nothing
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler#isApproved(org.springframework.security.oauth2.provider.AuthorizationRequest,
   *      org.springframework.security.core.Authentication)
   */
  @Override
  public boolean isApproved(AuthorizationRequest authorizationRequest,
      Authentication userAuthentication) {
    if (super.isApproved(authorizationRequest, userAuthentication)) {
      return true;
    }
    if (!userAuthentication.isAuthenticated()) {
      return false;
    }
    final OauthClientDetailsEntity clientDetails =
        oauthService.loadOauthClientDetails(authorizationRequest.getClientId());
    return clientDetails != null && clientDetails.isTrusted();

  }

  /**
   * Sets the oauth service.
   *
   * @param oauthService
   *          the new oauth service
   */
  public void setOauthService(OauthService oauthService) {
    this.oauthService = oauthService;
  }
}
