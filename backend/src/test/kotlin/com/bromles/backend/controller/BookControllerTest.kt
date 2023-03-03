package com.bromles.backend.controller

import com.bromles.backend.dto.BookFileDto
import com.bromles.backend.dto.BookRequestDto
import com.bromles.backend.dto.BookResponseDto
import com.bromles.backend.dto.UpdateBookRequestDto
import com.bromles.backend.service.BookService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.core.io.InputStreamResource
import org.springframework.mock.web.MockMultipartFile
import java.io.InputStream
import java.time.LocalDate

internal class BookControllerTest {

    private val bookService: BookService = mock()

    private val bookController = BookController(bookService)

    @Test
    fun getAllBook() {
        whenever(bookService.getAllBook())
            .thenReturn(listOf())
        val allBook = bookController.getAllBook()
        assertEquals(0, allBook.size)
    }

    @Test
    fun addBook() {
        whenever(bookService.createBook(any()))
            .thenReturn(getBookResponseDto())

        val bookResponseDto = bookController.addBook(
            getBookRequestDto()
        )
        assertEquals("name", bookResponseDto.name)
    }

    private fun getBookRequestDto() = BookRequestDto(
        "name",
        "author",
        MockMultipartFile("img", "img".toByteArray()),
        MockMultipartFile("file", "file".toByteArray()),
        "tagName",
        "category",
        LocalDate.now()
    )

    private fun getBookResponseDto() = BookResponseDto(
        1,
        "name",
        "author",
        "tagName",
        "img".toByteArray(),
        "category",
        LocalDate.now(),
        "username"
    )

    @Test
    fun getBook() {
        whenever(bookService.getBookDto(any()))
            .thenReturn(getBookResponseDto())

        val bookResponseDto = bookController.getBook(1)
        assertEquals("name", bookResponseDto.name)
    }

    @Test
    fun updateBook() {
        whenever(bookService.updateBook(any(), any()))
            .thenReturn(getBookResponseDto())

        bookController.updateBook(1, getUpdateBookRequestDto())
    }

    private fun getUpdateBookRequestDto() = UpdateBookRequestDto(
        "name",
        "author",
        "tagName",
        "category",
        LocalDate.now()
    )

    @Test
    fun deleteBook() {
        doNothing().whenever(bookService).delete(any())

        bookController.deleteBook(1)
    }

    @Test
    fun getBookFile() {
        whenever(bookService.getBookFile(any()))
            .thenReturn(BookFileDto(InputStreamResource(InputStream.nullInputStream()), "filename"))

        val bookFile = bookController.getBookFile(1)
        assertEquals("filename", bookFile.headers.contentDisposition.filename)
    }

    @Test
    fun getBookImg() {

        whenever(bookService.getBookImg(any()))
            .thenReturn(BookFileDto(InputStreamResource(InputStream.nullInputStream()), "filename"))

        val bookFile = bookController.getBookImg(1)
        assertEquals("filename", bookFile.headers.contentDisposition.filename)
    }
}