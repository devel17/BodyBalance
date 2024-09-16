package com.dev.bb.api.exception

import com.dev.bb.api.exception.custom.NotFoundEntityException
import org.apache.commons.lang3.ObjectUtils.defaultIfNull
import org.springframework.core.NestedExceptionUtils.getRootCause
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException.BadRequest
import org.springframework.web.client.HttpClientErrorException.NotFound


@RestControllerAdvice
class RestExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [Exception::class])
    fun handleException(e: Exception) : ErrorDto {
        return ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "General Error", defaultIfNull(getRootCause(e), e).toString())
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = [BadRequest::class])
    fun handleException(e: BadRequest) : ErrorDto {
        val rootCause = defaultIfNull(getRootCause(e), e)
        return ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), rootCause.message, rootCause.toString())
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [NotFoundEntityException::class])
    fun handleException(e: NotFoundEntityException) : ErrorDto {
        val rootCause = defaultIfNull(getRootCause(e), e)
        return ErrorDto(HttpStatus.NOT_FOUND.value(), rootCause.message, rootCause.toString())
    }
}