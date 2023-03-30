package com.bromles.backend.model

import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table


@Table
@Entity
class Tag(
    val name: String,
    @OneToMany
    val books: List<Book> = listOf(),
) : BaseEntity()
