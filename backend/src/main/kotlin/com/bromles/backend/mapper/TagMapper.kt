package com.bromles.backend.mapper


import com.bromles.backend.dto.TagDto
import com.bromles.backend.model.Tag
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface TagMapper {

    //    @Mapping(source = "tag.name", target = "tagName")
//    @Mapping(source = "category.name", target = "category")
    fun toTagDto(tag: Tag): TagDto
    fun toTagDto(tags: List<Tag>): List<TagDto>
}