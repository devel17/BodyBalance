package com.dev.bb.api.exception

data class ErrorDto(val code: Int, val message: String?, val caused: String)
