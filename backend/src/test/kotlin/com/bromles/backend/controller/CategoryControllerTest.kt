package com.bromles.backend.controller

import com.bromles.backend.dto.CategoryDto
import com.bromles.backend.service.CategoryService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

class CategoryControllerTest {
    private val categoryService: CategoryService = mock()
    private val categoryController = CategoryController(categoryService)

    @Test
    fun getAllCategories() {
        whenever(categoryService.getAllCategories())
            .thenReturn(listOf())
        val allCategories = categoryController.getAllCategories()
        Assertions.assertEquals(0, allCategories.size)
    }

    @Test
    fun addCategory() {
        whenever(categoryService.createCategory(any()))
            .thenReturn(getCategoryDto())

        val category = categoryService.createCategory(getCategoryDto())
        Assertions.assertEquals("name", category.name)
    }


    @Test
    fun getCategory() {
        whenever(categoryService.getCategory(any()))
            .thenReturn(getCategoryDto())

        val category = categoryController.getCategory(1)
        Assertions.assertEquals("name", category.name)
    }


   @Test
    fun modifyCategoryName() {
       whenever(categoryService.modifyCategory(any(), any()))
           .thenReturn(CategoryDto("newName"))

       val category = categoryController.modifyCategoryName(1, CategoryDto("newName"))
       Assertions.assertEquals("newName", category.name)
    }


    private fun getCategoryDto() = CategoryDto("name")

}