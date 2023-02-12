package com.bromles.backend.model

import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.*

@Table
@Entity
open class User(
    open val keycloak_id: UUID,
    @OneToMany
    open val role: List<Role>,
    @OneToOne
    open val setting: Setting,
) : BaseEntity()
