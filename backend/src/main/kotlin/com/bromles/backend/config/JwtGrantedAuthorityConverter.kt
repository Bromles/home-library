package com.bromles.backend.config

import org.springframework.core.convert.converter.Converter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt

fun interface JwtGrantedAuthorityConverter : Converter<Jwt?, Collection<GrantedAuthority?>?>
