package com.bromles.backend.model

import java.util.UUID
import javax.persistence.*

@Table
@Entity
open class User(
    open val keycloak_id: UUID,
    @OneToMany
    open val role: List<Role>,
    @OneToOne
    open val setting: Setting,
) : BaseEntity()