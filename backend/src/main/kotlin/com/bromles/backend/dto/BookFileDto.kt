package com.bromles.backend.dto

import org.springframework.core.io.InputStreamResource

class BookFileDto(
    val file: InputStreamResource,
    val filename: String,
)