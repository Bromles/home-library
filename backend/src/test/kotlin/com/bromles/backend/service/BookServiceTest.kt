package com.bromles.backend.service

import com.bromles.backend.dto.BookRequestDto
import com.bromles.backend.dto.UpdateBookRequestDto
import com.bromles.backend.exception.ForbiddenException
import com.bromles.backend.exception.NotFoundException
import com.bromles.backend.mapper.BookMapper
import com.bromles.backend.mapper.BookMapperImpl
import com.bromles.backend.model.Category
import com.bromles.backend.model.Tag
import com.bromles.backend.repository.BookRepository
import com.bromles.backend.test_utils.BookUtils.getBook
import com.bromles.backend.utils.SecurityUtil
import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.mock.web.MockMultipartFile
import java.time.LocalDate
import java.util.*


internal class BookServiceTest {
    private val bookRepository: BookRepository = mock()
    private val tagService: TagService = mock()
    private val categoryService: CategoryService = mock()
    private val bookMapper: BookMapper = BookMapperImpl()

    private val bookService: BookService = BookService(
        bookRepository,
        tagService,
        categoryService,
        bookMapper
    )

    @Test
    fun getAllBook() {
        whenever(bookRepository.findAll())
            .thenReturn(listOf())

        val allBook = bookService.getAllBook()

        assertEquals(0, allBook.size)
    }

    @Test
    fun getBookDto__ThrowException() {
        whenever(bookRepository.findById(any()))
            .thenReturn(Optional.empty())

        assertThrows(NotFoundException::class.java) { bookService.getBookDto(1) }
    }

    @Test
    fun getBookDto__Success() {
        whenever(bookRepository.findById(any()))
            .thenReturn(Optional.of(getBook()))

        assertEquals("name", bookService.getBookDto(1).name)
    }

    @Test
    fun getBookFile() {
        whenever(bookRepository.findById(any()))
            .thenReturn(Optional.of(getBook()))

        val bookFileDto = bookService.getBookFile(1)
        assertEquals("filename", bookFileDto.filename)
    }

    @Test
    fun getBookImg() {
        whenever(bookRepository.findById(any()))
            .thenReturn(Optional.of(getBook()))
        val bookFileDto = bookService.getBookImg(1)
        assertEquals("filename", bookFileDto.filename)
    }

    @Test
    fun createBook() {
        whenever(tagService.createOrGet(any()))
            .thenReturn(Tag("name"))
        whenever(categoryService.createOrGet(any()))
            .thenReturn(Category("name"))
        `when`(bookRepository.save(any()))
            .thenReturn(getBook())
        mockkObject(SecurityUtil)
        every { SecurityUtil.getUsername() } returns "username"
        MockMultipartFile(
            "file",
            "file".toByteArray()
        )
        MockMultipartFile(
                "img",
                "img".toByteArray()
        )

        val createBook = bookService.createBook(getBookRequestDto())
        assertEquals("name", createBook.name)
    }

    private fun getBookRequestDto() = BookRequestDto(
        "name",
        "author",
        MockMultipartFile(
            "img",
            "img".toByteArray()
        ),
        MockMultipartFile(
            "file",
            "file".toByteArray()
        ),
        "tagName",
        "category",
        LocalDate.now()
    )

    @Test
    fun delete__ForbiddenException() {
        whenever(bookRepository.findById(any()))
            .thenReturn(Optional.of(getBook()))

        mockkObject(SecurityUtil)
        every { SecurityUtil.getUsername() } returns "another_username"

        assertThrows(ForbiddenException::class.java) {
            bookService.delete(1)
        }
        verify(bookRepository) {
            0 * {deleteById(any())}
        }
    }

    @Test
    fun delete__Success() {
        whenever(bookRepository.findById(any()))
            .thenReturn(Optional.of(getBook()))
        doNothing().whenever(bookRepository)
            .deleteById(any())

        mockkObject(SecurityUtil)
        every { SecurityUtil.getUsername() } returns "username"

        bookService.delete(1)
        verify(bookRepository) {
            1 * {deleteById(any())}
        }
    }

    @Test
    fun updateBook__ForbiddenException() {
        whenever(bookRepository.findById(any()))
            .thenReturn(Optional.of(getBook()))

        mockkObject(SecurityUtil)
        every { SecurityUtil.getUsername() } returns "another_username"

        assertThrows(ForbiddenException::class.java) {
            bookService.updateBook(1, getUpdateBookRequestDto())
        }
    }

    @Test
    fun updateBook__Success() {
        whenever(bookRepository.findById(any()))
            .thenReturn(Optional.of(getBook()))
        whenever(tagService.createOrGet(any()))
            .thenReturn(Tag("name"))
        whenever(categoryService.createOrGet(any()))
            .thenReturn(Category("name"))
        `when`(bookRepository.save(any()))
            .thenReturn(getBook())

        mockkObject(SecurityUtil)
        every { SecurityUtil.getUsername() } returns "username"

        val updateBook =
            bookService.updateBook(1, getUpdateBookRequestDto())
        assertEquals("name", updateBook.name)
    }

    private fun getUpdateBookRequestDto() =
        UpdateBookRequestDto("name", "author", "tagName", "category", LocalDate.now())
}