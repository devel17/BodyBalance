package com.dev.bb.api.model.system

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "branches")
data class Branch(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false)
    var name: String = "",
    
    @Column(nullable = false)
    var address: String = "",
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    var organization: Organization? = null,
    
    @OneToMany(mappedBy = "branch", cascade = [CascadeType.ALL])
    var employees: MutableList<Employee> = mutableListOf(),
    
    @OneToMany(mappedBy = "branch", cascade = [CascadeType.ALL])
    var clients: MutableList<Client> = mutableListOf(),
    
    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        val EMPTY = Branch()
    }
} 