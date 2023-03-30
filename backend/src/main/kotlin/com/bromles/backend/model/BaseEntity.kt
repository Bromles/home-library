package com.bromles.backend.model

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0,

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    open var createdDate: LocalDateTime? = null,

    @UpdateTimestamp
    @Column(name = "UPDATED_DATE")
    open var updatedDate: LocalDateTime? = null,

    @Column(name = "CREATED_USER_ID")
    open var createdUserId: String? = null
)