/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * The Class AutowireHelper.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public final class AutowireHelper implements ApplicationContextAware {

  /** The Constant INSTANCE. */
  private static final AutowireHelper INSTANCE = new AutowireHelper();

  /** The application context. */
  private ApplicationContext applicationContext;

  /** The lock obj. */
  private final Object lockObj = new Object();

  /**
   * Instantiates a new autowire helper.
   */
  private AutowireHelper() {
  }

  /**
   * Tries to autowire the specified instance of the class if one of the
   * specified beans which need to be autowired are null.
   *
   * @param classToAutowire
   *          the instance of the class which holds @Autowire annotations
   * @param beansToAutowireInClass
   *          the beans which have the @Autowire annotation in the specified
   *          {#classToAutowire}
   */
  public void autowire(final Object classToAutowire, final Object... beansToAutowireInClass) {
    for (final Object bean : beansToAutowireInClass) {
      if (bean == null) {
        applicationContext.getAutowireCapableBeanFactory().autowireBean(classToAutowire);
        return;
      }
    }
  }

  /**
   * Autowire bean.
   *
   * @param bean
   *          the bean
   */
  public void autowireBean(final Object bean) {
    applicationContext.getAutowireCapableBeanFactory().autowireBean(bean);
  }

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
   */
  @Override
  public void setApplicationContext(final ApplicationContext applicationContext) {
    synchronized (lockObj) {
      this.applicationContext = applicationContext;
    }
  }

  /**
   * Gets the single instance of AutowireHelper.
   *
   * @return the singleton instance.
   */
  public static AutowireHelper getInstance() {
    return INSTANCE;
  }

}
