package com.dev.bb.api.model.system

import com.dev.bb.model.Domain
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "branches")
data class Branch(
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
    
) : Domain() {
    companion object {
        val EMPTY = Branch()
    }
} 