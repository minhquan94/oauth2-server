/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.config;

import static java.net.URLDecoder.decode;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

/**
 * The Class WebConfiguration.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
@Configuration
public class WebConfiguration
    implements ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory> {

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

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.boot.web.server.WebServerFactoryCustomizer#customize(org.springframework.boot.web.server.WebServerFactory)
   */
  @Override
  public void customize(WebServerFactory server) {
    setMimeMappings(server);
    // When running in an IDE or with ./mvnw spring-boot:run, set location of
    // the static web assets.
    setLocationForStaticAssets(server);
  }

  /**
   * Sets the mime mappings.
   *
   * @param server
   *          the new mime mappings
   */
  private void setMimeMappings(WebServerFactory server) {
    if (server instanceof ConfigurableServletWebServerFactory) {
      final MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
      // IE issue, see https://github.com/jhipster/generator-jhipster/pull/711
      mappings.add("html", MediaType.TEXT_HTML_VALUE + ";charset="
          + StandardCharsets.UTF_8.name().toLowerCase());
      // CloudFoundry issue, see
      // https://github.com/cloudfoundry/gorouter/issues/64
      mappings.add("json", MediaType.TEXT_HTML_VALUE + ";charset="
          + StandardCharsets.UTF_8.name().toLowerCase());
      final ConfigurableServletWebServerFactory servletWebServer =
          (ConfigurableServletWebServerFactory) server;
      servletWebServer.setMimeMappings(mappings);
    }
  }

  /**
   * Sets the location for static assets.
   *
   * @param server
   *          the new location for static assets
   */
  private void setLocationForStaticAssets(WebServerFactory server) {
    if (server instanceof ConfigurableServletWebServerFactory) {
      final ConfigurableServletWebServerFactory servletWebServer =
          (ConfigurableServletWebServerFactory) server;
      File root;
      final String prefixPath = resolvePathPrefix();
      root = new File(prefixPath + "target/www/");
      if (root.exists() && root.isDirectory()) {
        servletWebServer.setDocumentRoot(root);
      }
    }
  }

  /**
   * Resolve path prefix to static resources.
   *
   * @return the string
   */
  private String resolvePathPrefix() {
    String fullExecutablePath;
    try {
      fullExecutablePath =
          decode(this.getClass().getResource("").getPath(), StandardCharsets.UTF_8.name());
    } catch (final UnsupportedEncodingException e) {
      /* try without decoding if this ever happens */
      fullExecutablePath = this.getClass().getResource("").getPath();
      log.error("", e);
    }
    final String rootPath = Paths.get(".").toUri().normalize().getPath();
    final String extractedPath = fullExecutablePath.replace(rootPath, "");
    final int extractionEndIndex = extractedPath.indexOf("target/");
    if (extractionEndIndex <= 0) {
      return "";
    }
    return extractedPath.substring(0, extractionEndIndex);
  }
}
