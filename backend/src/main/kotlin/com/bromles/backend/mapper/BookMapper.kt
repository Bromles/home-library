package com.bromles.backend.mapper

import com.bromles.backend.dto.BookResponseDto
import com.bromles.backend.dto.UpdateBookRequestDto
import com.bromles.backend.model.Book
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface BookMapper {

    @Mapping(source = "tag.name", target = "tagName")
    @Mapping(source = "category.name", target = "category")
    fun toBookDto(book: Book): BookResponseDto

    fun toBookDto(book: List<Book>): List<BookResponseDto>


}