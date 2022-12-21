package com.bromles.backend.repository;

import com.bromles.backend.model.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : JpaRepository<Tag, Long> {

    fun findByName(name: String): Tag?
}