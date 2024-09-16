package com.dev.bb.api.configuration

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.List


@Configuration
@OpenAPIDefinition(info = Info(title = "Body Balance", version = "v1"))
open class OpenApiConfiguration {

    @Bean
    open fun commonApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .displayName("Информация о пользователе")
            .group("common")
            .packagesToScan("com.dev.bb.api.controller.common")
            .build()
    }
    @Bean
    open fun plusApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("plus")
            .displayName("Питание")
            .packagesToScan("com.dev.bb.api.controller.plus")
            .build()
    }

    @Bean
    open fun minusApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("minus")
            .displayName("Активность")
            .packagesToScan("com.dev.bb.api.controller.minus")
            .build()
    }
}

