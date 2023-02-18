package com.bromles.backend.controller

import com.bromles.backend.dto.CategoryDto
import com.bromles.backend.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/category")
class CategoryController(
    private val categoryService: CategoryService,
) {

    @GetMapping
    fun getAllCategories(): List<CategoryDto> =
        categoryService.getAllCategories()

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCategory(@RequestBody categoryDto: CategoryDto) =
        categoryService.createCategory(categoryDto)

    @GetMapping("/{id}")
    fun getCategory(@PathVariable id: Long): CategoryDto =
        categoryService.getCategory(id)


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    fun modifyCategoryName(@PathVariable id: Long, @RequestBody modifiedCategory: CategoryDto): CategoryDto =
        categoryService.modifyCategory(id, modifiedCategory)
}


