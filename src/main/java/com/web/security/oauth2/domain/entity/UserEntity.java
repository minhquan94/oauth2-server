/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.domain.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The Class CustomUserDetail.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
@Table(name = "User")
@Entity(name = "User")
public class UserEntity implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -8805807099326249976L;

  /** The id. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "userId", nullable = false, updatable = false)
  private Long id;

  /** The username. */
  @Column(name = "username", nullable = false, unique = true)
  private String username;

  /** The password. */
  @Column(name = "password", nullable = false)
  private String password;

  /** The enabled. */
  @Column(name = "enabled", nullable = false)
  private boolean enabled;

  /** The phone. */
  @Column(name = "phone")
  private String phone;

  /** The email. */
  @Column(name = "email")
  private String email;

  /** The last login time. */
  @Column(name = "lastLoginTime", nullable = true)
  private Timestamp lastLoginTime;

  /** The guid. */
  @Column(name = "guid", nullable = false)
  private String guid;

  /** The archived. */
  @Column(name = "archived")
  private boolean archived;

  /** The default user. */
  @Column(name = "defaultUser", nullable = true)
  private boolean defaultUser = false;

  /** The privileges. */
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private final List<UserPrivilegeEntity> privileges = new ArrayList<>();

  /**
   * The domain create time.
   */
  @Column(name = "createTime")
  private Timestamp createTime;

  /**
   * Gets the id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id
   *          the new id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the username.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username.
   *
   * @param username
   *          the new username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets the password.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password.
   *
   * @param password
   *          the new password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * Checks if is enabled.
   *
   * @return true, if is enabled
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * Sets the enabled.
   *
   * @param enabled
   *          the new enabled
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Gets the phone.
   *
   * @return the phone
   */
  public String getPhone() {
    return phone;
  }

  /**
   * Sets the phone.
   *
   * @param phone
   *          the new phone
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * Gets the email.
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email.
   *
   * @param email
   *          the new email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Gets the last login time.
   *
   * @return the last login time
   */
  public Timestamp getLastLoginTime() {
    return lastLoginTime;
  }

  /**
   * Sets the last login time.
   *
   * @param lastLoginTime
   *          the new last login time
   */
  public void setLastLoginTime(Timestamp lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
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
   * Checks if is default user.
   *
   * @return true, if is default user
   */
  public boolean isDefaultUser() {
    return defaultUser;
  }

  /**
   * Sets the default user.
   *
   * @param defaultUser
   *          the new default user
   */
  public void setDefaultUser(boolean defaultUser) {
    this.defaultUser = defaultUser;
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
   * Gets the guid.
   *
   * @return the guid
   */
  public String getGuid() {
    return guid;
  }

  /**
   * Gets the privileges.
   *
   * @return the privileges
   */
  public List<UserPrivilegeEntity> getPrivileges() {
    return privileges;
  }

}
