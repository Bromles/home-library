package com.bromles.backend.utils

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt

object SecurityUtil {

    fun getUsername(): String {
        val jwt = SecurityContextHolder.getContext().authentication.principal as Jwt
        return jwt.claims["preferred_username"] as String
    }
}