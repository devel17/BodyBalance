package com.dev.bb.api.model.system

import com.dev.bb.api.dto.admin.ClientDto
import jakarta.persistence.*
import java.time.LocalDateTime
import com.dev.bb.api.model.system.Branch
import com.dev.bb.model.Domain
import kotlin.random.Random

@Entity
@Table(name = "clients")
data class Client(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var phone: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    var branch: Branch,

) : Domain() {
    companion object {
        fun rand()= Client(
            name = listOf("Иван","Андрей").random(),
            email = "${Random.nextInt()}@mail.ru",
            phone = Random.toString(),
            branch = Branch.EMPTY
        )
    }

}

// Extension function for Client entity
fun com.dev.bb.api.model.system.Client.toDto() = ClientDto.fromEntity(this)
