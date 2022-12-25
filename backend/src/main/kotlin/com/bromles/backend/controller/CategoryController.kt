package com.bromles.backend.controller

import com.bromles.backend.dto.CategoryDto
import com.bromles.backend.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/category")
class CategoryController(
    private val categoryService: CategoryService,
) {

    @GetMapping
    fun getAllCategories(): List<CategoryDto> =
        categoryService.getAllCategories()

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun addCategory(@ModelAttribute categoryDto: CategoryDto) =
        categoryService.createCategory(categoryDto)

    @GetMapping("/{id}")
    fun getCategory(@PathVariable id: Long): CategoryDto =
        categoryService.getCategory(id)


    @PutMapping( path = ["/{id}"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun modifyCategoryName(@PathVariable id: Long, @ModelAttribute modifiedCategory: CategoryDto): CategoryDto =
        categoryService.modifyCategory(id, modifiedCategory)
}


