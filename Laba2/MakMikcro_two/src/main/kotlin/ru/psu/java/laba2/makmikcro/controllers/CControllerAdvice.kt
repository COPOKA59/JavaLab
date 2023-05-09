package ru.psu.java.laba2.makmikcro.controllers

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice//обработка исключений
class CControllerAdvice : ResponseEntityExceptionHandler() {
    @ExceptionHandler(
        value = [
            IllegalArgumentException::class,
            IllegalStateException::class
        ]
    )
    protected fun handleConfloct(
            ex: RuntimeException?, request: WebRequest?
    ): ResponseEntity<Any>{
        val bodyOfResponse = ""
        return handleExceptionInternal(
                ex!!, bodyOfResponse,
                HttpHeaders(), HttpStatus.CONFLICT, request!!
        )
    }
}