/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class PrivilegeConverter.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
@Converter
public class PrivilegeConverter implements AttributeConverter<Privilege, String> {

  /**
   * {@inheritDoc}
   *
   * @see javax.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.Object)
   */
  @Override
  public String convertToDatabaseColumn(Privilege privilege) {
    return privilege.name();
  }

  /**
   * {@inheritDoc}
   *
   * @see javax.persistence.AttributeConverter#convertToEntityAttribute(java.lang.Object)
   */
  @Override
  public Privilege convertToEntityAttribute(String arg0) {
    for (final Privilege privilege : Privilege.values()) {
      if (StringUtils.equalsIgnoreCase(privilege.name(), arg0)) {
        return privilege;
      }
    }
    return null;
  }

}
