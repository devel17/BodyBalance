package com.dev.bb.api.configuration.filter

import com.dev.bb.api.configuration.wrapper.UsernameHeaderRequestWrapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter
import io.jsonwebtoken.Jwts
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import java.util.*

class JwtHeaderEnrichmentFilter(private val publicKey: String) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // Извлекаем токен из заголовка Authorization
        val token = resolveToken(request)

        if (token != null && validateToken(token)) {
            // Извлекаем имя пользователя из токена
            val username = extractUsername(token)

            // Создаем кастомный HttpServletRequestWrapper для добавления заголовка
            val wrappedRequest = UsernameHeaderRequestWrapper(request, username)

            // Продолжаем цепочку фильтров с обновленным HttpServletRequest
            filterChain.doFilter(wrappedRequest, response)
        } else {
            // Если токен недействителен, просто продолжаем цепочку фильтров
            filterChain.doFilter(request, response)
        }
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (!bearerToken.isNullOrBlank() && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7) // Убираем "Bearer " из заголовка
        } else {
            null
        }
    }

    private fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(KeyFactory.getInstance("RSA")
                    .generatePublic(X509EncodedKeySpec(Base64.getDecoder()
                        .decode(publicKey)))) // Используем public key для проверки подписи
                .build()
                .parseClaimsJws(token)
            return true
        } catch (e: Exception) {
            println("Invalid JWT token: ${e.message}")
            return false
        }
    }

    private fun extractUsername(token: String): String {
        return Jwts.parserBuilder().setSigningKey(KeyFactory.getInstance("RSA")
            .generatePublic(X509EncodedKeySpec(Base64.getDecoder()
                .decode(publicKey)))) // Используем public key для проверки подписи
            .build()
            .parseClaimsJws(token)
            .body
            .subject // ИД пользователя обычно хранится в поле "sub" токена
    }
}