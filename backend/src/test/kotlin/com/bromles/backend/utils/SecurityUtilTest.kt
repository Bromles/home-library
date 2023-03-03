package com.bromles.backend.utils

import io.mockk.every
import io.mockk.mockkStatic
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import java.time.Instant

internal class SecurityUtilTest {

    @Test
    fun getUsername() {
        mockkStatic(SecurityContextHolder::class)
        val jwt = Jwt(
            "token",
            Instant.now(),
            Instant.now(),
            mapOf("header" to "header"),
            mapOf("preferred_username" to "username")
        )
        every { SecurityContextHolder.getContext().authentication.principal } returns jwt

        assertEquals("username", SecurityUtil.getUsername())
    }
}