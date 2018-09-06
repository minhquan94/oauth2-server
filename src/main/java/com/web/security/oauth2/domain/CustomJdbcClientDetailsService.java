/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.domain;

import javax.sql.DataSource;

import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

/**
 * The Class CustomJdbcClientDetailsService.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
public class CustomJdbcClientDetailsService extends JdbcClientDetailsService {

  /** The Constant SELECT_CLIENT_DETAILS_SQL. */
  private static final String SELECT_CLIENT_DETAILS_SQL =
      "select client_id, client_secret, resource_ids, scope, authorized_grant_types, "
          + "web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove "
          + "from oauth_client_details where client_id = ? and archived = 0 ";

  /**
   * Instantiates a new custom jdbc client details service.
   *
   * @param dataSource
   *          the data source
   */
  public CustomJdbcClientDetailsService(DataSource dataSource) {
    super(dataSource);
    setSelectClientDetailsSql(SELECT_CLIENT_DETAILS_SQL);
  }

}
