/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.rest;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.web.security.oauth2.domain.Privilege;
import com.web.security.oauth2.dto.UserFormDto;
import com.web.security.oauth2.service.UserService;
import com.web.security.oauth2.utils.CustomErrorType;

/**
 * The Class UserController.
 */
/**
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
@RestController
@RequestMapping("/account")
public class UserController {

  /** The Constant logger. */
  public static final Logger logger = LoggerFactory.getLogger(UserController.class);

  /** The user service. */
  @Autowired
  private UserService userService;

  /**
   * Creates the user.
   *
   * @param newUser
   *          the new user
   * @return the response entity
   */
  @CrossOrigin
  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ResponseEntity< ? > createUser(@RequestBody UserFormDto newUser) {
    if (userService.loadUserByUsername(newUser.getUsername()) != null) {
      logger.error("username already exist " + newUser.getUsername());
      return ResponseEntity.status(HttpStatus.CONFLICT).body(new CustomErrorType(
          "user with username " + newUser.getUsername() + "already exist "));
    }
    newUser.getPrivileges().add(Privilege.USER);
    userService.saveUser(newUser);
    return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
  }

  /**
   * User.
   *
   * @param principal
   *          the principal
   * @return the principal
   */
  @CrossOrigin
  @RequestMapping("/login")
  public Principal user(Principal principal) {
    logger.info("user logged " + principal);
    return principal;
  }
}
