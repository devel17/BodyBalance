package com.dev.bb.api.service

import com.dev.bb.api.model.system.Client
import com.dev.bb.api.repository.system.ClientRepository
import com.dev.bb.api.repository.system.BranchRepository
import com.dev.bb.api.dto.admin.ClientDto
import com.dev.bb.api.exception.custom.NotFoundEntityException
import com.dev.bb.api.model.system.Branch
import org.springframework.stereotype.Service

@Service
class ClientService(private val clientRepository: ClientRepository, private val branchRepository: BranchRepository) : IClientService {

    override fun getAllClients(): List<Client> {
        return clientRepository.findAll()
    }

    override fun createClient(clientDto: ClientDto): Long {
        val client = Client(
            name = clientDto.name,
            email = clientDto.email,
            phone = clientDto.phone,
            branch = branchRepository.findById(clientDto.branchId).orElseThrow {throw NotFoundEntityException("(\"Branch with id = ${clientDto.branchId} not found!\" )")}
            )

        return clientRepository.save(client).id
    }
} 