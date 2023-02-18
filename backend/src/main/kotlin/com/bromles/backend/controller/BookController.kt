package com.bromles.backend.controller

import com.bromles.backend.dto.BookRequestDto
import com.bromles.backend.dto.BookResponseDto
import com.bromles.backend.dto.UpdateBookRequestDto
import com.bromles.backend.service.BookService
import org.springframework.core.io.InputStreamResource
import org.springframework.http.*
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Validated
@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/api/v1/book")
class BookController(
    private val bookService: BookService,
) {

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    fun getAllBook(): List<BookResponseDto> =
        bookService.getAllBook()

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun addBook(@Valid @ModelAttribute bookRequestDto: BookRequestDto): BookResponseDto =
        bookService.createBook(bookRequestDto)

    @GetMapping("/{id}")
    fun getBook(@PathVariable id: Long): BookResponseDto =
        bookService.getBookDto(id)

    @PutMapping("/{id}")
    fun updateBook(@PathVariable id: Long, @RequestBody bookDto: UpdateBookRequestDto): BookResponseDto =
        bookService.updateBook(id, bookDto)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@PathVariable id: Long) =
        bookService.delete(id)

    @GetMapping("/{id}/file")
    fun getBookFile(@PathVariable id: Long): ResponseEntity<InputStreamResource> {
        val bookFile = bookService.getBookFile(id)
        val httpHeaders = HttpHeaders()
        httpHeaders.contentDisposition =
            ContentDisposition.builder("attachment")
                .filename(bookFile.filename).build()
        return ResponseEntity.status(HttpStatus.OK)
            .headers(httpHeaders)
            .body(bookFile.file)
    }

    @GetMapping("/{id}/img")
    fun getBookImg(@PathVariable id: Long): ResponseEntity<InputStreamResource> {
        val bookFile = bookService.getBookImg(id)
        val httpHeaders = HttpHeaders()
        httpHeaders.contentDisposition =
            ContentDisposition.builder("attachment")
                .filename(bookFile.filename).build()
        return ResponseEntity.status(HttpStatus.OK)
            .headers(httpHeaders)
            .body(bookFile.file)
    }
}