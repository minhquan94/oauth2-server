/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

/**
 * The Class WebConfiguration.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
@Configuration
public class WebConfiguration implements ServletContextInitializer {

  /** The Constant log. */
  private static final Logger log = LoggerFactory.getLogger(WebConfiguration.class);

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.boot.web.servlet.ServletContextInitializer#onStartup(javax.servlet.ServletContext)
   */
  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    initH2Console(servletContext);
  }

  /**
   * Inits the h2 console.
   *
   * @param servletContext
   *          the servlet context
   */
  private void initH2Console(ServletContext servletContext) {
    log.info("Starting H2 console");
    final ServletRegistration.Dynamic h2ConsoleServlet =
        servletContext.addServlet("H2Console", new org.h2.server.web.WebServlet());
    h2ConsoleServlet.setLoadOnStartup(1);
  }
}
