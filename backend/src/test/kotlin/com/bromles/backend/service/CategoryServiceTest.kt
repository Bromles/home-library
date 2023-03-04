package com.bromles.backend.service

import com.bromles.backend.dto.CategoryDto
import com.bromles.backend.exception.NotFoundException
import com.bromles.backend.mapper.CategoryMapper
import com.bromles.backend.mapper.CategoryMapperImpl
import com.bromles.backend.model.Category
import com.bromles.backend.repository.CategoryRepository
import com.bromles.backend.test_utils.BookUtils
import com.bromles.backend.test_utils.CategoryUtil.getCategory
import com.bromles.backend.test_utils.TagUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

internal class CategoryServiceTest {
    private val categoryRepository: CategoryRepository = mock()
    private val categoryMapper: CategoryMapper = CategoryMapperImpl()

    private val categoryService: CategoryService = CategoryService(
        categoryRepository,
        categoryMapper
    )

    @Test
    fun getAllCategories(){
        whenever(categoryRepository.findAll())
            .thenReturn(listOf())

        val allCategories = categoryService.getAllCategories()
        Assertions.assertEquals(0, allCategories.size)
    }

    @Test
    fun createCategory(){
        Mockito.`when`(categoryRepository.save(any()))
            .thenReturn(getCategory())

        val createCategory = categoryService.createCategory(CategoryDto("name"))
        Assertions.assertEquals("name", createCategory.name)
    }

    @Test
    fun getCategory__Success(){
        whenever(categoryRepository.findById(any()))
            .thenReturn(Optional.of(getCategory()))

        Assertions.assertEquals("name", categoryService.getCategory(1).name)
    }

    @Test
    fun getCategory__ThrowException(){
        whenever(categoryRepository.findById(any()))
            .thenReturn(Optional.empty())

        Assertions.assertThrows(NotFoundException::class.java) { categoryService.getCategory(1) }
    }

    @Test
    fun modifyCategory__Success(){
        whenever(categoryRepository.findById(any()))
            .thenReturn(Optional.of(getCategory()))

        val modifyCategory = categoryService.modifyCategory(1, CategoryDto("newName"))
        Assertions.assertEquals("newName", modifyCategory.name)

    }

    @Test
    fun modifyCategory__ThrowException(){
        whenever(categoryRepository.findById(any()))
            .thenReturn(Optional.empty())

        Assertions.assertThrows(NotFoundException::class.java) { categoryService.modifyCategory(1, CategoryDto("newName")) }
    }

    @Test
    fun createOrGet__Success(){
        Mockito.`when`(categoryRepository.findByName(any()))
            .thenReturn(getCategory())

        Assertions.assertEquals("name", categoryService.createOrGet("name").name)

    }

    @Test
    fun createOrGet__ThrowException(){
        whenever(categoryRepository.findByName(any()))
            .thenReturn(null)

        Assertions.assertThrows(NotFoundException::class.java) { categoryService.createOrGet("name") }
    }
}

