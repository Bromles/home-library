package com.bromles.backend.dto

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

data class BookRequestDto(
    val name: String = "123",
    val author: String  = "123",
    var file: MultipartFile,
    val tagName: String  = "123",
    val category: String = "123",
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val yearOfPublishing: LocalDate,
)