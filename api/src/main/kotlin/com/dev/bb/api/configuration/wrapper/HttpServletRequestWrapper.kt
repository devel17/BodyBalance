package com.dev.bb.api.configuration.wrapper

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import java.util.*

class UsernameHeaderRequestWrapper constructor(
    private val originalRequest: HttpServletRequest,
    private val username: String
) : HttpServletRequestWrapper(originalRequest) {

    override fun getHeader(name: String): String? {
        return if (name.equals("X-User-Id", ignoreCase = true)) {
            username // Возвращаем имя пользователя для заголовка X-User-Name
        } else {
            super.getHeader(name) // Для остальных заголовков используем оригинальные значения
        }
    }

    override fun getHeaderNames(): Enumeration<String> {
        val originalHeaders = originalRequest.headerNames.asSequence().toMutableSet()
        originalHeaders.add("X-User-Id") // Добавляем наш заголовок
        return Collections.enumeration(originalHeaders)
    }

    override fun getHeaders(name: String): Enumeration<String> {
        if (name.equals("X-User-Id", ignoreCase = true)) {
            return Collections.enumeration(listOf(username))
        }
        return super.getHeaders(name)
    }
}