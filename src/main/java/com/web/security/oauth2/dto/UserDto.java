/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.web.security.oauth2.domain.Privilege;
import com.web.security.oauth2.domain.entity.UserEntity;
import com.web.security.oauth2.domain.entity.UserPrivilegeEntity;
import com.web.security.oauth2.utils.DateUtils;

/**
 * The Class UserDto.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
public class UserDto implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -2502329463915439215L;

  /** The guid. */
  private String guid;

  /** The username. */
  private String username;

  /** The phone. */
  private String phone;

  /** The email. */
  private String email;

  /** The create time. */
  private String createTime;

  /** The privileges. */
  private List<Privilege> privileges = new ArrayList<>();

  /**
   * Instantiates a new user dto.
   */
  public UserDto() {
    // do nothing
  }

  /**
   * Instantiates a new user dto.
   *
   * @param user
   *          the user
   */
  public UserDto(UserEntity user) {
    this.guid = user.getGuid();
    this.username = user.getUsername();
    this.phone = user.getPhone();
    this.email = user.getEmail();
    this.privileges = user.getPrivileges().stream().map(UserPrivilegeEntity::getPrivilege)
        .collect(Collectors.toList());
    this.createTime = DateUtils.toDateTime(user.getCreateTime().toLocalDateTime());
  }

  /**
   * Gets the creates the time.
   *
   * @return the creates the time
   */
  public String getCreateTime() {
    return createTime;
  }

  /**
   * Sets the creates the time.
   *
   * @param createTime
   *          the new creates the time
   */
  public void setCreateTime(String createTime) {
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
  public List<Privilege> getPrivileges() {
    return privileges;
  }

  /**
   * Sets the privileges.
   *
   * @param privileges
   *          the new privileges
   */
  public void setPrivileges(List<Privilege> privileges) {
    this.privileges = privileges;
  }

  /**
   * To dtos.
   *
   * @param users
   *          the users
   * @return the list
   */
  public static List<UserDto> toDtos(List<UserEntity> users) {
    final List<UserDto> dtos = new ArrayList<>(users.size());
    for (final UserEntity user : users) {
      dtos.add(new UserDto(user));
    }
    return dtos;
  }
}
