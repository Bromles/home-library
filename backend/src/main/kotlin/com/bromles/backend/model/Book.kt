package com.bromles.backend.model

import com.bromles.backend.dto.BookRequestDto
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import javax.persistence.*

@Table
@Entity
class Book(
    @Lob
    val file: ByteArray,
    val name: String,
    val author: String,
    val yearOfPublishing: LocalDate,
    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    var tag: Tag? = null,
    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    var category: Category? = null,
) : BaseEntity() {

    constructor(bookRequestDto: BookRequestDto) : this(
        bookRequestDto.file.bytes,
        bookRequestDto.name,
        bookRequestDto.author,
        bookRequestDto.yearOfPublishing
    )
}