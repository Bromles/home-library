package com.bromles.backend.model

import javax.persistence.*

@Table
@Entity
open class User(
    @OneToMany
    open val role: List<Role>,
    @OneToOne
    open val setting: Setting,
) : BaseEntity()