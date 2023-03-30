package com.bromles.backend.model

import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table


@Table
@Entity
class Setting(
    @OneToOne
    @JoinColumn(name = "USER_ID")
    val user: User,
) : BaseEntity()
