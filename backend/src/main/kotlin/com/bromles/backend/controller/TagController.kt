package com.bromles.backend.controller


import com.bromles.backend.dto.TagDto
import com.bromles.backend.service.TagService
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tags")
class TagController(
    private val tagService: TagService,
) {

    @GetMapping
    fun getAllTags(): List<TagDto> =
        tagService.getAllTags()

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping //re
    @ResponseStatus(HttpStatus.CREATED)
    fun addTag(@RequestBody tagDto: TagDto): TagDto =
        tagService.addTag(tagDto)

    @GetMapping("/{id}")
    fun getTag(@PathVariable id: Long): TagDto =
        tagService.getTag(id)
}