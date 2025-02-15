package com.dev.bb.api.util


import io.jsonwebtoken.Jwts
import java.security.PublicKey
import java.util.Date

class JWTUtil(private val publicKey: PublicKey) {

    /**
     * Проверяет JWT-токен и извлекает данные (claims).
     *
     * @param token JWT-токен.
     * @return Map<String, Any> с декодированными данными.
     * @throws RuntimeException Если токен недействителен.
     */
    fun parseClaims(token: String): Map<String, Any> {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(publicKey) // Используем public key для проверки подписи
                .build()
                .parseClaimsJws(token)

            claims.body // Возвращаем все claims из тела токена
        } catch (e: Exception) {
            throw RuntimeException("Invalid JWT token", e)
        }
    }
}