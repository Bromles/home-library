package com.bromles.backend.repository;

import com.bromles.backend.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {

    fun findByName(name: String): Category?

    @Modifying
    @Query(nativeQuery = true, value = "delete from category")
    override fun deleteAll()

}