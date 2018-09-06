/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.web.security.oauth2.service.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.web.security.oauth2.domain.CustomUserDetails;
import com.web.security.oauth2.domain.entity.UserEntity;
import com.web.security.oauth2.domain.repository.UserRepository;
import com.web.security.oauth2.dto.UserDto;
import com.web.security.oauth2.dto.UserFormDto;
import com.web.security.oauth2.dto.UserJsonDto;
import com.web.security.oauth2.dto.UserOverviewDto;
import com.web.security.oauth2.service.UserService;
import com.web.security.oauth2.utils.WebUtils;

/**
 * The Class UserServiceImpl.
 *
 * @author <a href="mailto:minhquan9402@gmail.com">zuan_</a>
 */
@Service("userService")
public class UserServiceImpl implements UserService {

  /** The Constant LOG. */
  private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

  /** The user repository. */
  @Autowired
  private UserRepository userRepository;

  /**
   * {@inheritDoc}
   *
   * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
   */
  @Override
  @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
  public UserDetails loadUserByUsername(String username) {
    final UserEntity user = userRepository.findByUsername(username);
    if (user == null || user.isArchived()) {
      throw new UsernameNotFoundException("Not found any user for username[" + username + "]");
    }
    return new CustomUserDetails(user);
  }

  /**
   * {@inheritDoc}
   *
   * @see com.zuan.oauth2.service.UserService#loadCurrentUserJsonDto()
   */
  @Override
  @Transactional(readOnly = true)
  public UserJsonDto loadCurrentUserJsonDto() {
    final Authentication authentication =
        SecurityContextHolder.getContext().getAuthentication();
    final Object principal = authentication.getPrincipal();

    if (authentication instanceof OAuth2Authentication && (principal instanceof String
        || principal instanceof org.springframework.security.core.userdetails.User)) {
      return loadOauthUserJsonDto((OAuth2Authentication) authentication);
    } else {
      final CustomUserDetails userDetails = (CustomUserDetails) principal;
      return new UserJsonDto(userRepository.findByGuid(userDetails.user().getGuid()));
    }
  }

  /**
   * {@inheritDoc}
   *
   * @see com.zuan.oauth2.service.UserService#loadUserOverviewDto(com.zuan.oauth2.dto.UserOverviewDto)
   */
  @Override
  @Transactional(readOnly = true)
  public UserOverviewDto loadUserOverviewDto(UserOverviewDto overviewDto) {
    final UserEntity user = userRepository.findByUsername(overviewDto.getUsername());
    final List<UserEntity> users = new ArrayList<>();
    users.add(user);
    overviewDto.setUserDtos(UserDto.toDtos(users));
    return overviewDto;
  }

  /**
   * {@inheritDoc}
   *
   * @see com.zuan.oauth2.service.UserService#isExistedUsername(java.lang.String)
   */
  @Override
  @Transactional(readOnly = true)
  public boolean isExistedUsername(String username) {
    final UserEntity user = userRepository.findByUsername(username);
    return user != null;
  }

  /**
   * {@inheritDoc}
   *
   * @see com.zuan.oauth2.service.UserService#saveUser(com.zuan.oauth2.dto.UserFormDto)
   */
  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public String saveUser(UserFormDto formDto) {
    final UserEntity user = formDto.newUser();
    userRepository.save(user);
    LOG.debug("{}|Save User: {}", WebUtils.getIp(), user);
    return user.getGuid();
  }

  /**
   * Load oauth user json dto.
   *
   * @param oAuth2Authentication
   *          the o auth2 authentication
   * @return the user json dto
   */
  private UserJsonDto loadOauthUserJsonDto(OAuth2Authentication oAuth2Authentication) {
    final UserJsonDto userJsonDto = new UserJsonDto();
    userJsonDto.setUsername(oAuth2Authentication.getName());
    final Collection<GrantedAuthority> authorities = oAuth2Authentication.getAuthorities();
    for (final GrantedAuthority authority : authorities) {
      userJsonDto.getPrivileges().add(authority.getAuthority());
    }
    return userJsonDto;
  }
}
