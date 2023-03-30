package com.bromles.backend.model

import com.bromles.backend.dto.BookRequestDto
import com.bromles.backend.dto.UpdateBookRequestDto
import java.time.LocalDate
import javax.persistence.*

@Table
@Entity
class Book(
    var name: String,
    var author: String,
    var yearOfPublishing: LocalDate,
    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    var tag: Tag,
    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    var category: Category,
    @Lob
    var img: ByteArray,
    @Lob
    var file: ByteArray,
    var filename: String?,
) : BaseEntity() {

    constructor(bookRequestDto: BookRequestDto, tag: Tag, category: Category) : this(
        bookRequestDto.name,
        bookRequestDto.author,
        bookRequestDto.yearOfPublishing,
        tag,
        category,
        bookRequestDto.img.bytes,
        bookRequestDto.file.bytes,
        bookRequestDto.file.originalFilename,
    )

    fun update(bookDto: UpdateBookRequestDto) {
        this.name = bookDto.name
        this.author = bookDto.author
        this.yearOfPublishing = bookDto.yearOfPublishing
    }
}
