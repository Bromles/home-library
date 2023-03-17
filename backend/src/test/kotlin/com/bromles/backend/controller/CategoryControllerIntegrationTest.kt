package com.bromles.backend.controller

import com.bromles.backend.dto.CategoryDto
import com.bromles.backend.dto.TagDto
import com.bromles.backend.dto.UpdateBookRequestDto
import com.bromles.backend.model.Book
import com.bromles.backend.model.Category
import com.bromles.backend.model.Tag
import com.bromles.backend.repository.BookRepository
import com.bromles.backend.repository.CategoryRepository
import com.bromles.backend.repository.TagRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.*
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.put
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDate

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class CategoryControllerIntegrationTest {

    @Autowired
    private lateinit var context: WebApplicationContext

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private var mvc: MockMvc? = null

    @BeforeEach
    fun setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
            .build()
        categoryRepository.deleteAll()
    }

    @Test
    fun getAllCategories_Unauthorized() {
        mvc!!.get("/category")
            .andDo {
                handle {
                    println(it.response.contentAsString)
                }
            }.andExpect {
                status { isUnauthorized() }
            }
    }

    @Test
    fun getAllCategories_Empty() {
        mvc!!.get("/category") {
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
    fun getAllCategories_OneCategory() {
        val category = Category("categoryName")
        categoryRepository.save(category)

        mvc!!.get("/category")  {
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
            jsonPath("$[0].name") { value("categoryName") }
        }
    }

    @Test
    fun addCategory_Unauthorized(){
        val categoryDto = CategoryDto("categoryName")
        mvc!!.perform(
            MockMvcRequestBuilders.post("/category")
                .content(objectMapper.writeValueAsString(categoryDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo { println(it.response.contentAsString) }
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun addCategory_Forbidden(){
        val categoryDto = CategoryDto("categoryName")
        mvc!!.perform(
            MockMvcRequestBuilders.post("/category")
                .content(objectMapper.writeValueAsString(categoryDto))
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
    fun addCategory_Success(){
        val categoryDto = CategoryDto("categoryName")
        mvc!!.perform(
            MockMvcRequestBuilders.post("/category")
                .content(objectMapper.writeValueAsString(categoryDto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(
                    jwt()
                        .authorities(SimpleGrantedAuthority("ROLE_ADMIN"))
                        .jwt { it.claim("preferred_username", "name") }
                )
        )
            .andDo { println(it.response.contentAsString) }
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.name").value("categoryName"))


        val categories = categoryRepository.findAll()
        assertEquals(1, categories.size)
        assertEquals("categoryName", categories[0].name)
    }


    @Test
    fun getCategory_Unauthorized(){
        val savedCategory = categoryRepository.save(Category("categoryName"))

        mvc!!.get("/category/{id}", savedCategory.id)
            .andDo {
                handle {
                    println(it.response.contentAsString)
                }
            }.andExpect {
                status { isUnauthorized() }
            }
    }

    @Test
    fun getCategory_NotFound(){
        mvc!!.get("/category/{id}", 1) {
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
    fun getCategory_Success(){
        val savedCategory = categoryRepository.save(Category("categoryName"))

        mvc!!.get("/category/{id}", savedCategory.id) {
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
            jsonPath("$.name") { value("categoryName") }
        }
    }

    @Test
    fun modifyCategoryName_Unauthorized(){
        val existedCategory = categoryRepository.save(Category("categoryName"))
        val categoryDto = CategoryDto("categoryName")
        mvc!!.perform(
            MockMvcRequestBuilders.put("/category/{id}", existedCategory.id)
                .content(objectMapper.writeValueAsString(categoryDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo { println(it.response.contentAsString) }
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun modifyCategoryName_Forbidden(){
        val existedCategory = categoryRepository.save(Category("categoryName"))
        val categoryDto = CategoryDto("categoryName")
        mvc!!.perform(
            MockMvcRequestBuilders.put("/category/{id}", existedCategory.id)
                .content(objectMapper.writeValueAsString(categoryDto))
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
    fun modifyCategoryName_Success(){
        val oldCategory = categoryRepository.save(Category("categoryName"))
        val categoryDto = CategoryDto("categoryNewName")

        var categories = categoryRepository.findAll()
        println(categories[0].id)
        println(categories[0].name)

        mvc!!.perform(
            MockMvcRequestBuilders.put("/category/{id}", oldCategory.id)
                .content(objectMapper.writeValueAsString(categoryDto))
                .contentType(MediaType.APPLICATION_JSON)
                .with(
                    jwt()
                        .authorities(SimpleGrantedAuthority("ROLE_ADMIN"))
                        .jwt { it.claim("preferred_username", "name") }
                )
        )
            .andDo { println(it.response.contentAsString) }
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("categoryNewName"))

        categories = categoryRepository.findAll()
        println(categories[0].id)
        assertEquals("categoryNewName", categories[0].name)
    }



}