package com.bromles.backend.service

import com.bromles.backend.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun createUser() {

    }
}
