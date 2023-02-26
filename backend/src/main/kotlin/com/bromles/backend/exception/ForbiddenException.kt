package com.bromles.backend.exception

class ForbiddenException(
    override val message: String?
) : RuntimeException()