package com.bromles.backend.service


import com.bromles.backend.dto.TagDto
import com.bromles.backend.exception.NotFoundException
import com.bromles.backend.mapper.TagMapper
import com.bromles.backend.model.Tag
import com.bromles.backend.repository.TagRepository
import org.springframework.stereotype.Service

@Service
class TagService(
    private val tagRepository: TagRepository,
    private val tagMapper: TagMapper

) {
    fun getAllTags(): List<TagDto> =
        tagMapper.toTagDto(tagRepository.findAll())

    fun addTag(tagDto: TagDto): TagDto =
        tagMapper.toTagDto(tagRepository.save(Tag(tagDto.name)))

    fun getTag(id: Long): TagDto =
        tagMapper.toTagDto(
            tagRepository.findById(id)
                .orElseThrow { NotFoundException("Not found tag by id = $id") }
        )

    fun createOrGet(name: String): Tag {
        return tagRepository.findByName(name) ?: tagRepository.save(Tag(name))
    }
}