package com.dev.bb.api.model.system

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "positions")
data class Position(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false)
    var name: String,
    
    @Column(nullable = false)
    var description: String,
    
    @OneToMany(mappedBy = "position")
    var employees: MutableList<Employee> = mutableListOf(),
    
    @ManyToMany
    @JoinTable(
        name = "position_roles",
        joinColumns = [JoinColumn(name = "position_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableSet<Role> = mutableSetOf(),
    
    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) 