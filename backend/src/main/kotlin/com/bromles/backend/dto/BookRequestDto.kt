package com.bromles.backend.dto

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class BookRequestDto(
    @field:Size(min=3, max=50)
    val name: String,
    @field:Size(min=5, max=50)
    val author: String,
    var img: MultipartFile,
    var file: MultipartFile,
    @field:Size(min=1, max=50)
    val tagName: String,
    @field:Size(min=1, max=50)
    val category: String,
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val yearOfPublishing: LocalDate,
)