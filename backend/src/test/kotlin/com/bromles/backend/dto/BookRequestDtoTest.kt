package com.bromles.backend.dto

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.mock.web.MockMultipartFile
import java.time.LocalDate
import javax.validation.ConstraintViolation
import javax.validation.Validation

internal class BookRequestDtoTest{

    private var validator = Validation.buildDefaultValidatorFactory().usingContext().validator
    private var nameRight = getRandomString(50);

    @ParameterizedTest
    @CsvSource(
        "2, 4, 0, 0, false",
        "3, 5, 1, 1, true",
        "25, 25, 25, 25, true",
        "50, 50, 50, 50, true",
        "51, 51, 51, 51, false"
    )
    fun `validation`(name: Int, author: Int, tag: Int, cat: Int, isSuccess: Boolean) {
        val bookRequestDto = BookRequestDto(
            getRandomString(name),
            getRandomString(author),
            MockMultipartFile("img", "img".toByteArray()),
            MockMultipartFile("file", "file".toByteArray()),
            getRandomString(tag),
            getRandomString(cat),
            LocalDate.now()
        )
        val validates: Set<ConstraintViolation<BookRequestDto>> = validator.validate(bookRequestDto)
        assertEquals(isSuccess, validates.isEmpty())
    }

    fun getRandomString(length: Int): String {
        val charset = ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }

}