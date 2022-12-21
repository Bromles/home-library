package com.bromles.backend.mapper

import com.bromles.backend.dto.BookRequestDto
import com.bromles.backend.dto.BookResponseDto
import com.bromles.backend.model.Book
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface BookMapper {

//    @Mappings(
//        Mapping( target = "category", ignore = true),
//        Mapping(source = "file", target = "file", qualifiedByName = ["multipartFileToByteArray"]),
//    )
//    fun toBook(bookDto: BookDto): Book

    @Mapping(source = "tag.name", target = "tagName")
    @Mapping(source = "category.name", target = "category")
    fun toBookDto(book: Book): BookResponseDto

    fun toBookDto(book: List<Book>): List<BookResponseDto>
//
//    @Named("multipartFileToByteArray")
//    fun multipartFileToByteArray(file: MultipartFile): ByteArray {
//        return "file.bytes".toByteArray()
//    }
//    @Named("sdf")
//    fun sdf(file: String): String {
//        return file
//    }
}