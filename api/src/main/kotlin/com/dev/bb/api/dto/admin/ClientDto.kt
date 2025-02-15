package com.dev.bb.api.dto.admin

import com.dev.bb.api.model.system.Branch
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import kotlin.random.Random
import com.dev.bb.api.model.system.Client

data class ClientDto(
    val id: Long? = null,
    
    @field:NotBlank(message = "Name is required")
    val name: String,
    
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Invalid email format")
    val email: String,
    
    @field:NotBlank(message = "Phone is required")
    @field:Pattern(regexp = "^\\+?[0-9]{10,13}$", message = "Invalid phone format")
    val phone: String,
    
    val branchId: Long?,
) {
    companion object {
        fun fromEntity(client: com.dev.bb.api.model.system.Client) = ClientDto(
            id = client.id,
            name = client.name,
            email = client.email,
            phone = client.phone,
            branchId = client.branch?.id,
        )
        fun rand() = ClientDto(
            id = Random.nextLong(),
            name = listOf("Иван","Андрей").random(),
            email = "${Random.nextInt()}@mail.ru",
            phone = Random.toString(),
            branchId = Random.nextLong(),
        )
    }
}

