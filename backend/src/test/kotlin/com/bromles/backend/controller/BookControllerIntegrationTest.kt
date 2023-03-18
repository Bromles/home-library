package com.bromles.backend.controller

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
import org.springframework.test.web.servlet.delete
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
internal class BookControllerIntegrationTest {

    @Autowired
    private lateinit var context: WebApplicationContext

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

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
        bookRepository.deleteAll()
        tagRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    @Test
    fun getAllBook_Unauthorized() {
        mvc!!.get("/book")
            .andDo {
                handle {
                    println(it.response.contentAsString)
                }
            }.andExpect {
                status { isUnauthorized() }
            }
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
    fun getAllBook_OneBook() {
        val tag = Tag("tag")
        val category = Category("category")
        tagRepository.save(tag)
        categoryRepository.save(category)

        val book = Book(
            "name",
            "author",
            LocalDate.now(),
            tag,
            category,
            "img".toByteArray(),
            "file".toByteArray(),
            "filename"
        )
        book.createdUserId = "name"
        bookRepository.save(book)

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
            jsonPath("$[0].name") { value("name") }
            jsonPath("$[0].author") { value("author") }
            jsonPath("$[0].tagName") { value("tag") }
            jsonPath("$[0].category") { value("category") }
            jsonPath("$[0].yearOfPublishing") { value(LocalDate.now().toString()) }
        }
    }

    @Test
    fun addBook_NotFoundCategory() {
        val img = MockMultipartFile("img", "img".toByteArray())
        val file = MockMultipartFile("file", "file".toByteArray())
        mvc!!.perform(
            MockMvcRequestBuilders.multipart("/book")
                .file(img)
                .file(file)
                .param("name", "name")
                .param("author", "author")
                .param("tagName", "tagName")
                .param("category", "category")
                .param("yearOfPublishing", LocalDate.now().toString())
                .with(
                    jwt()
                        .authorities(SimpleGrantedAuthority("ROLE_USER"))
                        .jwt { it.claim("preferred_username", "name") }
                )
        )
            .andDo { println(it.response.contentAsString) }
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.info").value("not_found"))

    }

    @Test
    fun addBook_Success() {
        categoryRepository.save(Category("category"))

        val img = MockMultipartFile("img", "img".toByteArray())
        val file = MockMultipartFile("file", "file".toByteArray())
        mvc!!.perform(
            MockMvcRequestBuilders.multipart("/book")
                .file(img)
                .file(file)
                .param("name", "name")
                .param("author", "author")
                .param("tagName", "tagName")
                .param("category", "category")
                .param("yearOfPublishing", LocalDate.now().toString())
                .with(
                    jwt()
                        .authorities(SimpleGrantedAuthority("ROLE_USER"))
                        .jwt { it.claim("preferred_username", "name") }
                )
        )
            .andDo { println(it.response.contentAsString) }
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.name").value("name"))
            .andExpect(jsonPath("$.author").value("author"))
            .andExpect(jsonPath("$.tagName").value("tagName"))
            .andExpect(jsonPath("$.category").value("category"))
            .andExpect(jsonPath("$.yearOfPublishing").value(LocalDate.now().toString()))
            .andExpect(jsonPath("$.name").value("name"))

        assertEquals(1, tagRepository.findAll().size)
        val books = bookRepository.findAll()
        assertEquals(1, books.size)
        assertEquals("name", books[0].name)
        assertEquals("tagName", books[0].tag.name)
        assertEquals("category", books[0].category.name)
    }

    @Test
    fun getBook_NotFound() {
        mvc!!.get("/book/1") {
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
            jsonPath("$.info") { value("not_found") }
        }
    }

    @Test
    fun getBook_Success() {
        val tag = Tag("tag")
        val category = Category("category")
        tagRepository.save(tag)
        categoryRepository.save(category)

        val book = Book(
            "name",
            "author",
            LocalDate.now(),
            tag,
            category,
            "img".toByteArray(),
            "file".toByteArray(),
            "filename"
        )
        book.createdUserId = "name"
        val savedBook = bookRepository.save(book)

        mvc!!.get("/book/" + savedBook.id) {
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
            jsonPath("$.name") { value("name") }
            jsonPath("$.author") { value("author") }
            jsonPath("$.tagName") { value("tag") }
            jsonPath("$.category") { value("category") }
        }
    }

    @Test
    fun updateBook_NotFoundBook() {
        mvc!!.put("/book/1") {
            with(
                jwt()
                    .authorities(SimpleGrantedAuthority("ROLE_USER"))
                    .jwt { it.claim("preferred_username", "name") }
            )
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                UpdateBookRequestDto(
                    "name",
                    "author",
                    "tagName",
                    "category",
                    LocalDate.now()
                )
            )
        }.andDo {
            handle {
                println(it.response.contentAsString)
            }
        }.andExpect {
            status { isNotFound() }
            jsonPath("$.info") { value("not_found") }
        }
    }

    @Test
    fun updateBook_Forbidden() {
        val tag = Tag("tag")
        val category = Category("category")
        tagRepository.save(tag)
        categoryRepository.save(category)

        val book = Book(
            "name",
            "author",
            LocalDate.now(),
            tag,
            category,
            "img".toByteArray(),
            "file".toByteArray(),
            "filename"
        )
        book.createdUserId = "nothing"
        val savedBook = bookRepository.save(book)
        mvc!!.put("/book/" + savedBook.id) {
            with(
                jwt()
                    .authorities(SimpleGrantedAuthority("ROLE_USER"))
                    .jwt { it.claim("preferred_username", "name") }
            )
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                UpdateBookRequestDto(
                    "name",
                    "author",
                    "tagName",
                    "category",
                    LocalDate.now()
                )
            )
        }.andDo {
            handle {
                println(it.response.contentAsString)
            }
        }.andExpect {
            status { isForbidden() }
            jsonPath("$.info") { value("forbidden") }
        }

        assertEquals(1, bookRepository.findAll().size)
        assertEquals(1, categoryRepository.findAll().size)
        assertEquals(1, tagRepository.findAll().size)
    }

    @Test
    fun updateBook_NotFoundCategory() {
        val tag = Tag("tagName")
        val category = Category("category")
        tagRepository.save(tag)
        categoryRepository.save(category)

        val book = Book(
            "name",
            "author",
            LocalDate.now(),
            tag,
            category,
            "img".toByteArray(),
            "file".toByteArray(),
            "filename"
        )
        book.createdUserId = "name"
        val savedBook = bookRepository.save(book)
        mvc!!.put("/book/" + savedBook.id) {
            with(
                jwt()
                    .authorities(SimpleGrantedAuthority("ROLE_USER"))
                    .jwt { it.claim("preferred_username", "name") }
            )
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                UpdateBookRequestDto(
                    "name",
                    "author",
                    "tagName",
                    "anotherCategory",
                    LocalDate.now()
                )
            )
        }.andDo {
            handle {
                println(it.response.contentAsString)
            }
        }.andExpect {
            status { isNotFound() }
            jsonPath("$.info") { value("not_found") }
        }

        assertEquals(1, bookRepository.findAll().size)
        assertEquals(1, categoryRepository.findAll().size)
        assertEquals(1, tagRepository.findAll().size)
    }

    @Test
    fun updateBook_Success() {
        val tag = Tag("tagName")
        val category = Category("category")
        tagRepository.save(tag)
        categoryRepository.save(category)

        val book = Book(
            "name",
            "author",
            LocalDate.now(),
            tag,
            category,
            "img".toByteArray(),
            "file".toByteArray(),
            "filename"
        )
        book.createdUserId = "name"
        val savedBook = bookRepository.save(book)
        mvc!!.put("/book/" + savedBook.id) {
            with(
                jwt()
                    .authorities(SimpleGrantedAuthority("ROLE_USER"))
                    .jwt { it.claim("preferred_username", "name") }
            )
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                UpdateBookRequestDto(
                    "name",
                    "author",
                    "tagName",
                    "category",
                    LocalDate.now()
                )
            )
        }.andDo {
            handle {
                println(it.response.contentAsString)
            }
        }.andExpect {
            status { isOk() }
            jsonPath("$.name") { value("name") }
            jsonPath("$.author") { value("author") }
            jsonPath("$.tagName") { value("tagName") }
            jsonPath("$.category") { value("category") }
        }

        assertEquals(1, bookRepository.findAll().size)
        assertEquals(1, categoryRepository.findAll().size)
        assertEquals(1, tagRepository.findAll().size)
    }

    @Test
    fun updateBook_Success_CreateTag() {
        val tag = Tag("tag")
        val category = Category("category")
        tagRepository.save(tag)
        categoryRepository.save(category)

        val book = Book(
            "name",
            "author",
            LocalDate.now(),
            tag,
            category,
            "img".toByteArray(),
            "file".toByteArray(),
            "filename"
        )
        book.createdUserId = "name"
        val savedBook = bookRepository.save(book)
        mvc!!.put("/book/" + savedBook.id) {
            with(
                jwt()
                    .authorities(SimpleGrantedAuthority("ROLE_USER"))
                    .jwt { it.claim("preferred_username", "name") }
            )
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(
                UpdateBookRequestDto(
                    "name",
                    "author",
                    "newTag",
                    "category",
                    LocalDate.now()
                )
            )
        }.andDo {
            handle {
                println(it.response.contentAsString)
            }
        }.andExpect {
            status { isOk() }
            jsonPath("$.name") { value("name") }
            jsonPath("$.author") { value("author") }
            jsonPath("$.tagName") { value("newTag") }
            jsonPath("$.category") { value("category") }
        }

        assertEquals(1, bookRepository.findAll().size)
        assertEquals(1, categoryRepository.findAll().size)
        assertEquals(2, tagRepository.findAll().size)
    }

    @Test
    fun deleteBook_NotFound() {
        mvc!!.delete("/book/1") {
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
    fun deleteBook_Forbidden() {
        val tag = Tag("tag")
        val category = Category("category")
        tagRepository.save(tag)
        categoryRepository.save(category)

        val book = Book(
            "name",
            "author",
            LocalDate.now(),
            tag,
            category,
            "img".toByteArray(),
            "file".toByteArray(),
            "filename"
        )
        book.createdUserId = "anotherName"
        val savedBook = bookRepository.save(book)
        mvc!!.delete("/book/" + savedBook.id) {
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
            status { isForbidden() }
        }

    }

    @Test
    fun deleteBook_Success() {
        val tag = Tag("tag")
        val category = Category("category")
        tagRepository.save(tag)
        categoryRepository.save(category)

        val book = Book(
            "name",
            "author",
            LocalDate.now(),
            tag,
            category,
            "img".toByteArray(),
            "file".toByteArray(),
            "filename"
        )
        book.createdUserId = "name"
        val savedBook = bookRepository.save(book)
        mvc!!.delete("/book/" + savedBook.id) {
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
            status { isNoContent() }
        }

        assertEquals(0, bookRepository.findAll().size)
        assertEquals(1, categoryRepository.findAll().size)
        assertEquals(1, tagRepository.findAll().size)
    }
}