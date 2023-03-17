package com.bromles.backend.controller

import com.bromles.backend.dto.TagDto
import com.bromles.backend.model.Tag
import com.bromles.backend.repository.TagRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class TagControllerIntegrationTest {

    @Autowired
    private lateinit var context: WebApplicationContext

    @Autowired
    private lateinit var tagRepository: TagRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private var mvc: MockMvc? = null

    @BeforeEach
    fun setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
            .build()
        tagRepository.deleteAll()
    }

    @Test
    fun getAllTag_Unauthorized() {
        mvc!!.get("/tags")
            .andDo {
                handle {
                    println(it.response.contentAsString)
                }
            }.andExpect {
                status { isUnauthorized() }
            }
    }

    @Test
    fun getAllTag_Empty() {
        mvc!!.get("/tags") {
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
    fun getAllTag_OneTag() {
        val tag = Tag("tagName")
        tagRepository.save(tag)

        mvc!!.get("/tags")  {
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
            jsonPath("$[0].name") { value("tagName") }
        }
    }

    @Test
    fun addTag_Unauthorized(){
        val tagDto: TagDto = TagDto("tagName")
        mvc!!.perform(
            MockMvcRequestBuilders.post("/tags")
                .content(objectMapper.writeValueAsString(tagDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo { println(it.response.contentAsString) }
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun addTag_Forbidden(){
        val tagDto: TagDto = TagDto("tagName")
        mvc!!.perform(
            MockMvcRequestBuilders.post("/tags")
                .content(objectMapper.writeValueAsString(tagDto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(
                    jwt()
                        .authorities(SimpleGrantedAuthority("ROLE_USER"))
                        .jwt { it.claim("preferred_username", "name") }
                )
        )
            .andDo { println(it.response.contentAsString) }
            .andExpect(status().isForbidden)
    }

    @Test
    fun addTag_Success(){
        val tagDto = TagDto("tagName")
        mvc!!.perform(
            MockMvcRequestBuilders.post("/tags")
                .content(objectMapper.writeValueAsString(tagDto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(
                    jwt()
                        .authorities(SimpleGrantedAuthority("ROLE_ADMIN"))
                        .jwt { it.claim("preferred_username", "name") }
                )
        )
            .andDo { println(it.response.contentAsString) }
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.name").value("tagName"))


        val tags = tagRepository.findAll()
        assertEquals(1, tags.size)
        assertEquals("tagName", tags[0].name)
    }


    @Test
    fun getTag_Unauthorized(){
        val savedTag = tagRepository.save(Tag("tagName"))

        mvc!!.get("/tags/{id}", savedTag.id)
            .andDo {
                handle {
                    println(it.response.contentAsString)
                }
            }.andExpect {
                status { isUnauthorized() }
            }
    }

    @Test
    fun getTag_NotFound(){
        mvc!!.get("/tags/{id}", 1) {
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
            status { isNotFound() }
        }
    }

    @Test
    fun getTag_Success(){
        val savedTag = tagRepository.save(Tag("tagName"))

        mvc!!.get("/tags/{id}", savedTag.id) {
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
            jsonPath("$.name") { value("tagName") }
        }
    }

}