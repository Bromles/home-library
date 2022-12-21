package com.bromles.backend.model

import com.bromles.backend.listener.BaseEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
@EntityListeners(BaseEntityListener::class)
abstract class BaseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0,

    @Column(name = "CREATED_DATE")
    open var createdDate: LocalDateTime? = null,

    @Column(name = "UPDATED_DATE")
    open var updatedDate: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_USER_ID")
    open var createdUser: User? = null
)