package com.bromles.backend.exception

class NotFoundException(
    override val message: String?
) : RuntimeException(message) {

}