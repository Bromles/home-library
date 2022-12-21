package com.bromles.backend.service

import com.bromles.backend.model.Tag
import com.bromles.backend.repository.TagRepository
import org.springframework.stereotype.Service

@Service
class TagService(
    private val tagRepository: TagRepository,
) {

    fun createOrGet(name: String): Tag {
        return tagRepository.findByName(name) ?: tagRepository.save(Tag(name))
    }
}