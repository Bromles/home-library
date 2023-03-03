package com.bromles.backend.dto

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile
import java.time.LocalDate
import javax.validation.ConstraintViolation
import javax.validation.Validation

internal class BookRequestDtoTest{

    private var validator = Validation.buildDefaultValidatorFactory().usingContext().validator

    @Test
    fun validation__Success() {
        val validates: Set<ConstraintViolation<BookRequestDto>> = validator.validate(getBookRequestDto())
        assertTrue(validates.isEmpty())
    }

    @Test
    fun validation__Failure() {
        val bookRequestDto = BookRequestDto(
            "n",
            "a",
            MockMultipartFile("img", "img".toByteArray()),
            MockMultipartFile("file", "file".toByteArray()),
            "",
            "",
            LocalDate.now()
        )
        val validates: Set<ConstraintViolation<BookRequestDto>> = validator.validate(bookRequestDto)
        assertFalse(validates.isEmpty())
        assertEquals(4, validates.size)
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
}