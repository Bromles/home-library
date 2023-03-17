package com.bromles.backend.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*


@RestControllerAdvice
class ExceptionHandlerController : ResponseEntityExceptionHandler() {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(
        ex: NotFoundException
    ): ResponseEntity<ErrorInfo> {
        return ResponseEntity(ErrorInfo("", "not_found"), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(
        ex: ForbiddenException
    ): ResponseEntity<ErrorInfo> {
        return ResponseEntity(ErrorInfo("", "forbidden"), HttpStatus.FORBIDDEN)
    }

    class ErrorInfo internal constructor(val url: String, val info: String)
}