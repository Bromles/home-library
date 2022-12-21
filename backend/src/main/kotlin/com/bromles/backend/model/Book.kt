package com.bromles.backend.model

import com.bromles.backend.dto.BookRequestDto
import java.time.LocalDate
import javax.persistence.*

@Table
@Entity
class Book(
    val name: String,
    val author: String,
    val yearOfPublishing: LocalDate,
    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    var tag: Tag,
    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    var category: Category,
    @Lob
    val file: ByteArray,
    val filename: String?,
) : BaseEntity() {

    constructor(bookRequestDto: BookRequestDto, tag: Tag, category: Category) : this(
        bookRequestDto.name,
        bookRequestDto.author,
        bookRequestDto.yearOfPublishing,
        tag,
        category,
        bookRequestDto.file.bytes,
        bookRequestDto.file.originalFilename,
    )
}