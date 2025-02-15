package com.dev.bb.api.controller.admin

import com.dev.bb.api.components.KeycloakAdminClient
import com.dev.bb.api.dto.admin.ClientDto
import com.dev.bb.api.service.IClientService
import com.dev.bb.api.model.system.Client
import com.dev.bb.api.model.system.toDto
import com.dev.bb.api.repository.system.BranchRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import jakarta.validation.Valid

@RestController
@RequestMapping("/admin/client")
@Tag(name ="Контроллер управления клиентами", description ="CRUD операции")
class ClientController(
    val keycloakAdminClient: KeycloakAdminClient,
    val clientService: IClientService
) {

    @GetMapping("/all")
    @Operation(summary = "Получение всех клиентов")
    fun getAll(@RequestHeader("X-User-Id") userName: String): List<ClientDto> {
        val clients = clientService.getAllClients()
        return clients.map { ClientDto.fromEntity(it) }
    }

    @PostMapping
    @Operation(summary = "Создание нового клиента")
    fun createClient(
        @RequestHeader("X-User-Id") userName: String,
        @Valid @RequestBody clientDto: ClientDto
    ): ResponseEntity<Long?> {
        val clientId = clientService.createClient(clientDto)
        return ResponseEntity(clientId, HttpStatus.CREATED)
    }
}