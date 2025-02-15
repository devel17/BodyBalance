package com.dev.bb.api.service

import com.dev.bb.api.dto.admin.ClientDto
import com.dev.bb.api.model.system.Client
interface IClientService {
    fun getAllClients(): List<Client>
    fun createClient(client: ClientDto): Long?
} 