package com.bromles.backend.service

import com.bromles.backend.repository.TagRepository
import com.bromles.backend.dto.BookRequestDto
import com.bromles.backend.dto.TagDto
import com.bromles.backend.dto.UpdateBookRequestDto
import com.bromles.backend.exception.ForbiddenException
import com.bromles.backend.exception.NotFoundException
import com.bromles.backend.mapper.BookMapper
import com.bromles.backend.mapper.BookMapperImpl
import com.bromles.backend.mapper.TagMapper
import com.bromles.backend.mapper.TagMapperImpl
import com.bromles.backend.model.Book
import com.bromles.backend.model.Category
import com.bromles.backend.model.Tag
import com.bromles.backend.repository.BookRepository
import com.bromles.backend.test_utils.BookUtils.getBook
import com.bromles.backend.test_utils.TagUtils.getTag
import com.bromles.backend.utils.SecurityUtil
import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.mock.web.MockMultipartFile
import java.time.LocalDate
import java.util.*

internal class TagServiceTest {
    private val tagRepository: TagRepository = mock()
    private val tagMapper: TagMapper = TagMapperImpl()

    private val tagService: TagService = TagService(
        tagRepository,
        tagMapper
    )

    @Test
    fun getAllTags(){
        whenever(tagRepository.findAll())
            .thenReturn(listOf())

        val allTags = tagService.getAllTags()
        assertEquals(0, allTags.size)
    }

    @Test
    fun addTag(){
        `when`(tagRepository.save(any()))
            .thenReturn(getTag())

        val addTag = tagService.addTag(TagDto("name"))
        assertEquals("name", addTag.name)
    }

    @Test
    fun getTag__Success(){
        whenever(tagRepository.findById(any()))
            .thenReturn(Optional.of(getTag()))
        assertEquals("name", tagService.getTag(1).name)
    }

    @Test
    fun getTag__ThrowException(){
        whenever(tagRepository.findById(any()))
            .thenReturn(Optional.empty())

        assertThrows(NotFoundException::class.java) { tagService.getTag(1) }
    }

    @Test
    fun createOrGet__Create(){
        `when`(tagRepository.save(any()))
            .thenReturn(getTag())

        val tag = tagService.createOrGet("name")
        assertEquals("name", tag.name)
    }

    @Test
    fun createOrGet__Get(){
        `when`(tagRepository.findByName(any()))
            .thenReturn(getTag())

        val tag = tagService.createOrGet("name")
        assertEquals("name", tag.name)
    }
}