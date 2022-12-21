package com.bromles.backend.service

import com.bromles.backend.exception.NotFoundException
import com.bromles.backend.model.Category
import com.bromles.backend.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
) {

    fun createOrGet(name: String): Category =
        categoryRepository.findByName(name)
            ?: throw NotFoundException("Not found category by name = $name")

}