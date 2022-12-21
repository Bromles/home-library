package com.bromles.backend.controller

import com.bromles.backend.dto.BookRequestDto
import com.bromles.backend.dto.BookResponseDto
import com.bromles.backend.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/book")
class BookController(
    private val bookService: BookService,
) {

    @GetMapping
    fun getAllBook(): List<BookResponseDto> =
        bookService.getAllBook()

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun addBook(@ModelAttribute bookRequestDto: BookRequestDto): BookResponseDto =
        bookService.createBook(bookRequestDto)

    @GetMapping("/{id}")
    fun getBook(@PathVariable id: Long): BookResponseDto =
        bookService.getBook(id)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun addBook(@PathVariable id: Long) =
        bookService.delete(id)

    @GetMapping("/{id}/file")
    fun getBookFile(@PathVariable id: Long): ByteArray =
        bookService.getBookFile(id)


}