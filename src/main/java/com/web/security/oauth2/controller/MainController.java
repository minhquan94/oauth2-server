/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.web.security.oauth2.utils.WebUtils;

/**
 * The Class MainController.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
@Controller
public class MainController {

  /** The Constant LOG. */
  private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

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
   * Login.
   *
   * @return the string
   */
  @GetMapping(value = {"/login" })
  public String login() {
    LOG.info("Go to login, IP: {}", WebUtils.getIp());
    return INDEX;
  }

}
