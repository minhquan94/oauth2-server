/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.web.security.oauth2.domain.Privilege;
import com.web.security.oauth2.domain.PrivilegeConverter;

/**
 * The Class PrivilegeEntity.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
@Table(name = "User_Privilege")
@Entity(name = "UserPrivilege")
public class UserPrivilegeEntity implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -983244812040000903L;

  /** The id. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "userPrivilegeId", nullable = false, updatable = false)
  private Long id;

  /** The id. */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId")
  private UserEntity user;

  /** The privilege. */
  @Column(name = "privilege", nullable = false)
  @Convert(converter = PrivilegeConverter.class)
  private Privilege privilege;

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
   * Gets the user.
   *
   * @return the user
   */
  public UserEntity getUser() {
    return user;
  }

  /**
   * Sets the user.
   *
   * @param user
   *          the new user
   */
  public void setUser(UserEntity user) {
    this.user = user;
  }

  /**
   * Gets the privilege.
   *
   * @return the privilege
   */
  public Privilege getPrivilege() {
    return privilege;
  }

  /**
   * Sets the privilege.
   *
   * @param privilege
   *          the new privilege
   */
  public void setPrivilege(Privilege privilege) {
    this.privilege = privilege;
  }

}
