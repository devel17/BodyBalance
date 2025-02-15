package com.dev.bb.api.model.system

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false, unique = true)
    var name: String,
    
    @Column(nullable = false)
    var description: String,
    
    @ManyToMany(mappedBy = "roles")
    var positions: MutableSet<Position> = mutableSetOf(),
    
    @OneToMany(mappedBy = "role", cascade = [CascadeType.ALL])
    var permissions: MutableSet<Permission> = mutableSetOf(),
    
    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
