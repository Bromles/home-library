package com.bromles.backend.model

import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table


@Table
@Entity
class Setting(
    @OneToOne
    @JoinColumn(name = "USER_ID")
    val user: User,
) : BaseEntity()
