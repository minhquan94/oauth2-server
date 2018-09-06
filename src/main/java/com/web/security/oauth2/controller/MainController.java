/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Class MainController.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
@Controller
public class MainController {

  /** The Constant INDEX. */
  private static final String INDEX = "index";

  /**
   * Index.
   *
   * @return the string
   */
  @RequestMapping(value = "/")
  public String index() {
    return INDEX;
  }

  /**
   * Home.
   *
   * @return the string
   */
  @RequestMapping(value = "/home")
  public String home() {
    return INDEX;
  }

  /**
   * Logout.
   *
   * @return the string
   */
  @RequestMapping(value = "/logout")
  public String logout() {
    return INDEX;
  }

  /**
   * Login.
   *
   * @return the string
   */
  @GetMapping(value = {"/login" })
  public String login() {
    return INDEX;
  }
}
