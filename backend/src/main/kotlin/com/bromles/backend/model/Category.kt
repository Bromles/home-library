package com.bromles.backend.model

import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table


@Table
@Entity
class Category(
    var name: String,
    @OneToMany
    val books: List<Book> = listOf(),
) : BaseEntity()
