package com.bromles.backend.model

import javax.persistence.*


@Table
@Entity
class Role(
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ROLE")
    val user: List<User>
) : BaseEntity()
