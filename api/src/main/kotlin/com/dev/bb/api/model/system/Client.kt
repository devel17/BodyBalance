package com.dev.bb.api.model.system

import com.dev.bb.api.dto.admin.ClientDto
import jakarta.persistence.*
import java.time.LocalDateTime
import com.dev.bb.api.model.system.Branch
import kotlin.random.Random

@Entity
@Table(name = "clients")
data class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var phone: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    var branch: Branch,

    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun rand()= Client(
            id = Random.nextLong(),
            name = listOf("Иван","Андрей").random(),
            email = "${Random.nextInt()}@mail.ru",
            phone = Random.toString(),
            branch = Branch.EMPTY
        )
    }

}

// Extension function for Client entity
fun com.dev.bb.api.model.system.Client.toDto() = ClientDto.fromEntity(this)
