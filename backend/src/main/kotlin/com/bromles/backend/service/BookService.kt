package com.bromles.backend.service

import com.bromles.backend.dto.BookFileDto
import com.bromles.backend.dto.BookRequestDto
import com.bromles.backend.dto.BookResponseDto
import com.bromles.backend.dto.UpdateBookRequestDto
import com.bromles.backend.exception.ForbiddenException
import com.bromles.backend.exception.NotFoundException
import com.bromles.backend.mapper.BookMapper
import com.bromles.backend.model.Book
import com.bromles.backend.model.Category
import com.bromles.backend.model.Tag
import com.bromles.backend.repository.BookRepository
import com.bromles.backend.utils.SecurityUtil
import org.springframework.core.io.InputStreamResource
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val tagService: TagService,
    private val categoryService: CategoryService,
    private val bookMapper: BookMapper,
) {

    fun getAllBook(): List<BookResponseDto> =
        bookMapper.toBookDto(bookRepository.findAll())

    fun getBookDto(id: Long): BookResponseDto =
        bookMapper.toBookDto(getBook(id))

    fun getBookFile(id: Long): BookFileDto {
        val book = getBook(id)
        val resource = InputStreamResource(ByteArrayInputStream(book.file))
        return BookFileDto(resource, book.filename ?: "${book.name} ${book.author}")
    }

    fun getBookImg(id: Long): BookFileDto {
        val book = getBook(id)
        val resource = InputStreamResource(ByteArrayInputStream(book.img))
        return BookFileDto(resource, book.filename ?: "${book.name} ${book.author}")
    }

    private fun getBook(id: Long): Book =
        bookRepository.findById(id)
            .orElseThrow { NotFoundException("Not found book by id = $id") }

    fun createBook(bookRequestDto: BookRequestDto): BookResponseDto {
        val tag: Tag = tagService.createOrGet(bookRequestDto.tagName)
        val category: Category = categoryService.createOrGet(bookRequestDto.category)
        val toBook = Book(bookRequestDto, tag, category)
        toBook.createdUserId = SecurityUtil.getUsername()
        return bookMapper.toBookDto(bookRepository.save(toBook))
    }

    fun delete(id: Long) {
        val book = getBook(id)
        if (SecurityUtil.getUsername() != book.createdUserId) {
            throw ForbiddenException("No access to delete book id = $id")
        }
        bookRepository.deleteById(id)
    }

    fun updateBook(id: Long, bookDto: UpdateBookRequestDto): BookResponseDto {
        val book = getBook(id)
        if (SecurityUtil.getUsername() != book.createdUserId) {
            throw ForbiddenException("No access to delete book id = $id")
        }
        book.update(bookDto)
        val tag: Tag = tagService.createOrGet(bookDto.tagName)
        val category: Category = categoryService.createOrGet(bookDto.category)
        book.tag = tag
        book.category = category
        return bookMapper.toBookDto(bookRepository.save(book));
    }
}