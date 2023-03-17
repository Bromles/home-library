package com.bromles.backend.controller

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.*
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class BookControllerIntegrationTest {

    @Autowired
    private lateinit var context: WebApplicationContext

    private var mvc: MockMvc? = null

    @BeforeEach
    fun setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
            .build()
    }

    @Test
    fun getAllBook_Empty() {
        mvc!!.get("/book") {
            with(
                jwt()
                    .authorities(SimpleGrantedAuthority("ROLE_USER"))
                    .jwt { it.claim("preferred_username", "name") }
            )
        }.andDo {
            handle {
                println(it.response.contentAsString)
            }
        }.andExpect {
            status { isOk() }
            jsonPath("$") { isArray() }
            content { json("[]") }
        }
    }

    @Test
    fun addBook() {
    }

    @Test
    fun getBook() {
    }

    @Test
    fun updateBook() {
    }

    @Test
    fun deleteBook() {
    }

    @Test
    fun getBookFile() {
    }

    @Test
    fun getBookImg() {
    }
}