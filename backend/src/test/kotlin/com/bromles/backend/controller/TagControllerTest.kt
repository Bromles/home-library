package com.bromles.backend.controller

import com.bromles.backend.dto.TagDto
import com.bromles.backend.service.TagService
import org.junit.jupiter.api.Assertions
import org.mockito.kotlin.mock
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

internal class TagControllerTest {
    private val tagService: TagService = mock()
    private val tagController: TagController = TagController(tagService)

    @Test
    fun getAllTags() {
        whenever(tagService.getAllTags())
            .thenReturn(listOf())

        val allTags = tagController.getAllTags()
        Assertions.assertEquals(0, allTags.size)
    }

    @Test
    fun addTag() {
        whenever(tagService.addTag(any()))
            .thenReturn(getTagDto())

        Assertions.assertEquals("name", tagController.addTag(getTagDto()).name)
    }

    @Test
    fun getTag() {
        whenever(tagService.getTag(any()))
            .thenReturn(getTagDto())

        Assertions.assertEquals("name", tagController.getTag(1).name)
    }

    private fun getTagDto() = TagDto("name")
}