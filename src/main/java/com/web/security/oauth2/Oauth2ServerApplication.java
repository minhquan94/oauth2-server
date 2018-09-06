/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.web.security.oauth2.domain.repository.BaseJpaRepositoryImpl;
import com.web.security.oauth2.utils.AutowireHelper;

/**
 * The Class Oauth2ServerApplication.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {
    "com.web.security.oauth2.domain.repository" }, repositoryBaseClass = BaseJpaRepositoryImpl.class)
@EntityScan(basePackages = "com.web.security.oauth2.domain.entity")
public class Oauth2ServerApplication {

  /**
   * The main method.
   *
   * @param args
   *          the arguments
   */
  public static void main(String[] args) {
    final SpringApplication application = new SpringApplication(Oauth2ServerApplication.class);
    final ApplicationContext context = application.run(args);
    // Set the ApplicationContext that this object runs in.
    AutowireHelper.getInstance().setApplicationContext(context);
  }
}
