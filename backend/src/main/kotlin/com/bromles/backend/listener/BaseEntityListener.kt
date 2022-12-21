package com.bromles.backend.listener

import com.bromles.backend.model.BaseEntity
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@Component
class BaseEntityListener {
    @PrePersist
    fun onCreate(baseEntity: BaseEntity) {
        baseEntity.createdDate = LocalDateTime.now()
        baseEntity.updatedDate = LocalDateTime.now()
//        baseEntity.createdUser = currentAuthentication().userDetails
    }

    @PreUpdate
    fun onUpdate(baseEntity: BaseEntity) {
        baseEntity.updatedDate = LocalDateTime.now()
    }
}