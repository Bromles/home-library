package com.bromles.backend.model


import java.util.*
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Table
@Entity
open class User(
    open val keycloak_id: UUID,
    @OneToMany
    open val role: List<Role>,
    @OneToOne
    open val setting: Setting,
) : BaseEntity()
