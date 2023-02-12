package com.bromles.backend.mapper


import com.bromles.backend.dto.CategoryDto
import com.bromles.backend.model.Category
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface CategoryMapper {

    //    @Mapping(source = "tag.name", target = "tagName")
//    @Mapping(source = "category.name", target = "category")
    fun toCategoryDto(category: Category): CategoryDto
    fun toCategoryDto(categories: List<Category>): List<CategoryDto>
}