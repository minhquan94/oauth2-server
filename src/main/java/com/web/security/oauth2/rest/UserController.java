/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.rest;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class UserController.
 */
@RestController
public class UserController {

  /**
   * User.
   *
   * @param principal
   *          the principal
   * @return the principal
   */
  @GetMapping("/userinfo")
  public Principal user(Principal principal) {
    return principal;
  }
}
