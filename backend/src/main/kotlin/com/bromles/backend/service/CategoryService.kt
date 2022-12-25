package com.bromles.backend.service

import com.bromles.backend.dto.CategoryDto
import com.bromles.backend.exception.NotFoundException
import com.bromles.backend.mapper.CategoryMapper
import com.bromles.backend.model.Category
import com.bromles.backend.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val categoryMapper: CategoryMapper,
) {

    fun getAllCategories(): List<CategoryDto> =
        categoryMapper.toCategoryDto(categoryRepository.findAll())

    fun createCategory(categoryDto: CategoryDto): CategoryDto =
        categoryMapper.toCategoryDto(categoryRepository.save(Category(categoryDto.name)))

    fun getCategory(id: Long): CategoryDto =
        categoryMapper.toCategoryDto(categoryRepository.findById(id)
            .orElseThrow { NotFoundException("Not found category by id = $id") }
        )

    fun modifyCategory(id: Long, categoryDto: CategoryDto) : CategoryDto {
        val category = categoryRepository.findById(id)
            .orElseThrow { NotFoundException("Not found category by id = $id") }
        category.name = categoryDto.name
        categoryRepository.save(category)
        return categoryMapper.toCategoryDto(category)
    }
    fun createOrGet(name: String): Category =
        categoryRepository.findByName(name)
            ?: throw NotFoundException("Not found category by name = $name")

}