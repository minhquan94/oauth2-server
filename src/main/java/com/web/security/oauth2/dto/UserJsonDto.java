/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.web.security.oauth2.domain.entity.UserEntity;

/**
 * The Class UserJsonDto.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
public class UserJsonDto implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -704681024783524371L;

  /** The guid. */
  private String guid;

  /** The archived. */
  private boolean archived;

  /** The username. */
  private String username;

  /** The phone. */
  private String phone;

  /** The email. */
  private String email;

  /** The privileges. */
  private List<String> privileges = new ArrayList<>();

  /**
   * Instantiates a new user json dto.
   */
  public UserJsonDto() {
    // do nothing
  }

  /**
   * Instantiates a new user json dto.
   *
   * @param user
   *          the user
   */
  public UserJsonDto(UserEntity user) {
    this.guid = user.getGuid();
    this.archived = user.isArchived();
    this.username = user.getUsername();
    this.phone = user.getPhone();
    this.email = user.getEmail();
    user.getPrivileges()
        .forEach(privilege -> this.privileges.add(privilege.getPrivilege().name()));
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
   * Gets the guid.
   *
   * @return the guid
   */
  public String getGuid() {
    return guid;
  }

  /**
   * Sets the guid.
   *
   * @param guid
   *          the new guid
   */
  public void setGuid(String guid) {
    this.guid = guid;
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
   * Gets the privileges.
   *
   * @return the privileges
   */
  public List<String> getPrivileges() {
    return privileges;
  }

  /**
   * Sets the privileges.
   *
   * @param privileges
   *          the new privileges
   */
  public void setPrivileges(List<String> privileges) {
    this.privileges = privileges;
  }
}
