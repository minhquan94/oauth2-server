/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.domain.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class OauthClientDetailsEntity.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
@Table(name = "Oauth_Client_Details")
@Entity(name = "OauthClientDetails")
public class OauthClientDetailsEntity implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -6947822646185526939L;

  /** The client id. */
  @Id
  @Column(name = "client_id", nullable = false, unique = true)
  private String clientId;

  /** The resource ids. */
  @Column(name = "resource_ids")
  private String resourceIds;

  /** The client secret. */
  @Column(name = "client_secret")
  private String clientSecret;

  /** Available values: read,write. */
  @Column(name = "scope")
  private String scope;

  /**
   * grant types include "authorization_code", "password", "assertion", and
   * "refresh_token". Default value is "authorization_code,refresh_token".
   */
  @Column(name = "authorized_grant_types")
  private String authorizedGrantTypes = "authorization_code,refresh_token";

  /**
   * The re-direct URI(s) established during registration (optional, comma
   * separated).
   */
  @Column(name = "web_server_redirect_uri")
  private String webServerRedirectUri;

  /**
   * Authorities that are granted to the client (comma-separated). Distinct from
   * the authorities granted to the user on behalf of whom the client is acting.
   * <p/>
   * For example: ROLE_USER
   */
  @Column(name = "authorities")
  private String authorities;

  /**
   * The access token validity period in seconds (optional). If unspecified a
   * global default will be applied by the token services.
   */
  @Column(name = "access_token_validity")
  private Integer accessTokenValidity;

  /**
   * The refresh token validity period in seconds (optional). If unspecified a
   * global default will be applied by the token services.
   */
  @Column(name = "refresh_token_validity")
  private Integer refreshTokenValidity;

  /** The additional information. */
  @Column(name = "additional_information")
  private String additionalInformation;

  /** The create time. */
  @Column(name = "create_time")
  private Timestamp createTime;

  /** The archived. */
  @Column(name = "archived")
  private boolean archived = false;

  /**
   * The client is trusted or not. If it is trust, will skip approve step
   * default false.
   */
  @Column(name = "trusted")
  private boolean trusted = false;

  /** Value is 'true' or 'false', default 'false'. */
  @Column(name = "autoapprove")
  private String autoApprove;

  /**
   * Gets the client id.
   *
   * @return the client id
   */
  public String getClientId() {
    return clientId;
  }

  /**
   * Sets the client id.
   *
   * @param clientId
   *          the new client id
   */
  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  /**
   * Gets the resource ids.
   *
   * @return the resource ids
   */
  public String getResourceIds() {
    return resourceIds;
  }

  /**
   * Sets the resource ids.
   *
   * @param resourceIds
   *          the new resource ids
   */
  public void setResourceIds(String resourceIds) {
    this.resourceIds = resourceIds;
  }

  /**
   * Gets the client secret.
   *
   * @return the client secret
   */
  public String getClientSecret() {
    return clientSecret;
  }

  /**
   * Sets the client secret.
   *
   * @param clientSecret
   *          the new client secret
   */
  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  /**
   * Gets the scope.
   *
   * @return the scope
   */
  public String getScope() {
    return scope;
  }

  /**
   * Sets the scope.
   *
   * @param scope
   *          the new scope
   */
  public void setScope(String scope) {
    this.scope = scope;
  }

  /**
   * Gets the authorized grant types.
   *
   * @return the authorized grant types
   */
  public String getAuthorizedGrantTypes() {
    return authorizedGrantTypes;
  }

  /**
   * Sets the authorized grant types.
   *
   * @param authorizedGrantTypes
   *          the new authorized grant types
   */
  public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
    this.authorizedGrantTypes = authorizedGrantTypes;
  }

  /**
   * Gets the web server redirect uri.
   *
   * @return the web server redirect uri
   */
  public String getWebServerRedirectUri() {
    return webServerRedirectUri;
  }

  /**
   * Sets the web server redirect uri.
   *
   * @param webServerRedirectUri
   *          the new web server redirect uri
   */
  public void setWebServerRedirectUri(String webServerRedirectUri) {
    this.webServerRedirectUri = webServerRedirectUri;
  }

  /**
   * Gets the authorities.
   *
   * @return the authorities
   */
  public String getAuthorities() {
    return authorities;
  }

  /**
   * Sets the authorities.
   *
   * @param authorities
   *          the new authorities
   */
  public void setAuthorities(String authorities) {
    this.authorities = authorities;
  }

  /**
   * Gets the access token validity.
   *
   * @return the access token validity
   */
  public Integer getAccessTokenValidity() {
    return accessTokenValidity;
  }

  /**
   * Sets the access token validity.
   *
   * @param accessTokenValidity
   *          the new access token validity
   */
  public void setAccessTokenValidity(Integer accessTokenValidity) {
    this.accessTokenValidity = accessTokenValidity;
  }

  /**
   * Gets the refresh token validity.
   *
   * @return the refresh token validity
   */
  public Integer getRefreshTokenValidity() {
    return refreshTokenValidity;
  }

  /**
   * Sets the refresh token validity.
   *
   * @param refreshTokenValidity
   *          the new refresh token validity
   */
  public void setRefreshTokenValidity(Integer refreshTokenValidity) {
    this.refreshTokenValidity = refreshTokenValidity;
  }

  /**
   * Gets the additional information.
   *
   * @return the additional information
   */
  public String getAdditionalInformation() {
    return additionalInformation;
  }

  /**
   * Sets the additional information.
   *
   * @param additionalInformation
   *          the new additional information
   */
  public void setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

  /**
   * Gets the creates the time.
   *
   * @return the creates the time
   */
  public Timestamp getCreateTime() {
    return createTime;
  }

  /**
   * Sets the creates the time.
   *
   * @param createTime
   *          the new creates the time
   */
  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  /**
   * Checks if is archived.
   *
   * @return true, if is archived
   */
  public boolean isArchived() {
    return archived;
  }

  /**
   * Sets the archived.
   *
   * @param archived
   *          the new archived
   */
  public void setArchived(boolean archived) {
    this.archived = archived;
  }

  /**
   * Checks if is trusted.
   *
   * @return true, if is trusted
   */
  public boolean isTrusted() {
    return trusted;
  }

  /**
   * Sets the trusted.
   *
   * @param trusted
   *          the new trusted
   */
  public void setTrusted(boolean trusted) {
    this.trusted = trusted;
  }

  /**
   * Gets the auto approve.
   *
   * @return the auto approve
   */
  public String getAutoApprove() {
    return autoApprove;
  }

  /**
   * Sets the auto approve.
   *
   * @param autoApprove
   *          the new auto approve
   */
  public void setAutoApprove(String autoApprove) {
    this.autoApprove = autoApprove;
  }

}
