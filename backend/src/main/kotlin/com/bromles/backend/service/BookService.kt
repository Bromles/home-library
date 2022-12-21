package com.bromles.backend.service

import com.bromles.backend.dto.BookRequestDto
import com.bromles.backend.dto.BookResponseDto
import com.bromles.backend.exception.NotFoundException
import com.bromles.backend.mapper.BookMapper
import com.bromles.backend.model.Book
import com.bromles.backend.model.Category
import com.bromles.backend.model.Tag
import com.bromles.backend.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val tagService: TagService,
    private val categoryService: CategoryService,
    private val bookMapper: BookMapper,
) {
    fun getAllBook(): List<BookResponseDto> = bookMapper.toBookDto(bookRepository.findAll())

    fun getBook(id: Long): BookResponseDto =
        bookMapper.toBookDto(bookRepository.findById(id)
            .orElseThrow { NotFoundException("Not found book by id = $id") }
        )

    fun getBookFile(id: Long): ByteArray =
        bookRepository.findById(id)
            .orElseThrow { NotFoundException("Not found book by id = $id") }
            .file

    fun createBook(bookRequestDto: BookRequestDto): BookResponseDto {
        val tag: Tag = tagService.createOrGet(bookRequestDto.tagName)
        val category: Category = categoryService.createOrGet(bookRequestDto.category)
        val toBook = Book(bookRequestDto)
        toBook.tag = tag
        toBook.category = category
        return bookMapper.toBookDto(bookRepository.save(toBook))
    }

    fun delete(id: Long) =
        bookRepository.deleteById(id)
}