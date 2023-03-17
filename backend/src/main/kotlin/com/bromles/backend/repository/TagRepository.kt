package com.bromles.backend.repository;

import com.bromles.backend.model.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : JpaRepository<Tag, Long> {

    fun findByName(name: String): Tag?

    @Modifying
    @Query(nativeQuery = true, value = "delete from tag")
    override fun deleteAll()
}