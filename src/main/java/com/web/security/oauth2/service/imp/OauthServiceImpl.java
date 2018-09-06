/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.service.imp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.web.security.oauth2.domain.entity.OauthClientDetailsEntity;
import com.web.security.oauth2.domain.repository.OauthClientDetailsRepository;
import com.web.security.oauth2.dto.OauthClientDetailsDto;
import com.web.security.oauth2.service.OauthService;
import com.web.security.oauth2.utils.WebUtils;

/**
 * The Class OauthServiceImpl.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
@Service("oauthService")
public class OauthServiceImpl implements OauthService {

  /** The Constant LOG. */
  private static final Logger LOG = LoggerFactory.getLogger(OauthServiceImpl.class);

  /** The oauth repository. */
  @Autowired
  private OauthClientDetailsRepository oauthRepository;

  /**
   * {@inheritDoc}
   *
   * @see com.zuan.oauth2.service.OauthService#loadOauthClientDetails(java.lang.String)
   */
  @Override
  @Transactional(readOnly = true)
  public OauthClientDetailsEntity loadOauthClientDetails(String clientId) {
    return oauthRepository.findOauthClientDetails(clientId);
  }

  /**
   * {@inheritDoc}
   *
   * @see com.zuan.oauth2.service.OauthService#loadAllOauthClientDetailsDtos()
   */
  @Override
  @Transactional(readOnly = true)
  public List<OauthClientDetailsDto> loadAllOauthClientDetailsDtos() {
    final List<OauthClientDetailsEntity> clientDetailses = oauthRepository.findAll();
    return OauthClientDetailsDto.toDtos(clientDetailses);
  }

  /**
   * {@inheritDoc}
   *
   * @see com.zuan.oauth2.service.OauthService#archiveOauthClientDetails(java.lang.String)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void archiveOauthClientDetails(String clientId) {
    oauthRepository.updateOauthClientDetailsArchive(clientId, true);
    LOG.debug("{}|Update OauthClientDetails(clientId: {}) archive = true", WebUtils.getIp(),
        clientId);
  }

  /**
   * {@inheritDoc}
   *
   * @see com.zuan.oauth2.service.OauthService#loadOauthClientDetailsDto(java.lang.String)
   */
  @Override
  @Transactional(readOnly = true)
  public OauthClientDetailsDto loadOauthClientDetailsDto(String clientId) {
    final OauthClientDetailsEntity oauthClientDetails =
        oauthRepository.findOauthClientDetails(clientId);
    return oauthClientDetails != null ? new OauthClientDetailsDto(oauthClientDetails) : null;
  }

  /**
   * {@inheritDoc}
   *
   * @see com.zuan.oauth2.service.OauthService#registerClientDetails(com.zuan.oauth2.dto.OauthClientDetailsDto)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void registerClientDetails(OauthClientDetailsDto formDto) {
    final OauthClientDetailsEntity clientDetails = formDto.createDomain();
    oauthRepository.save(clientDetails);
    LOG.debug("{}|Save OauthClientDetails: {}", WebUtils.getIp(), clientDetails);
  }

}
