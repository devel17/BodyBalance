package com.dev.bb.api.configuration

import com.dev.bb.api.configuration.filter.JwtHeaderEnrichmentFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.servlet.handler.HandlerMappingIntrospector
import java.util.stream.Stream

@Configuration
@EnableWebSecurity
open class Security(@param:Value("\${keycloak.publicKey}") private val publicKey: String) {

    @Bean
    open fun securityFilterChain(http: HttpSecurity, introspector: HandlerMappingIntrospector): SecurityFilterChain {
        http.oauth2ResourceServer {
            oauth2 -> oauth2.jwt(Customizer.withDefaults())
        }
        http.cors { cors ->
            cors.configurationSource { request ->
                val corsConfig = CorsConfiguration().apply {
                    allowedOrigins = listOf("http://localhost:3000") // Разрешенные домены
                    allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешенные методы
                    allowedHeaders = listOf("X-Requested-With","Content-Type","Authorization","Origin",
                        "Accept", "Access-Control-Allow-Headers", "Access-Control-Allow-Origin",
                        "Access-Control-Allow-Methods", "Strict-Transport-Security",
                        "content-security-policy","x-content-type-options", "X-Frame-Options",
                        "Permissions-Policy") // Разрешенные заголовки
                    allowCredentials = true // Разрешить передачу cookies/credentials
                }
                corsConfig
            }
        }

        val mvcMatcherBuilder : MvcRequestMatcher.Builder = MvcRequestMatcher.Builder(introspector)
        return http
            .authorizeHttpRequests { c ->
                c.requestMatchers(mvcMatcherBuilder.pattern("/error")).permitAll()
                    .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
                    .requestMatchers(mvcMatcherBuilder.pattern("/system/**")).authenticated()
                    //.anyRequest().permitAll()
                    .and()
                    .addFilterBefore(JwtHeaderEnrichmentFilter(publicKey), UsernamePasswordAuthenticationFilter::class.java)

            }
        .build();
    }

    @Bean
    open fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val converter = JwtAuthenticationConverter()
        val jwtGrantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
        converter.setPrincipalClaimName("preferred_username")
        converter.setJwtGrantedAuthoritiesConverter { jwt ->
            val authorities = jwtGrantedAuthoritiesConverter.convert(jwt).also { println(it) }
            val roles = jwt.getClaimAsMap("realm_access")["roles"] as List<String>
            Stream.concat(
                authorities.stream(),
                roles.also { println(it) }.stream()
                    .filter { role: String -> role.startsWith("ROLE_") }
                    .map { role: String? ->
                        SimpleGrantedAuthority(
                            role
                        )
                    }
                    .map { obj: SimpleGrantedAuthority? ->
                        GrantedAuthority::class.java.cast(
                            obj
                        )
                    }
            )
                .toList()
        }

        return converter
    }

}