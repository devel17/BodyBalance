package com.dev.bb.api.model.system

import jakarta.persistence.*
import java.time.LocalDateTime
import com.dev.bb.api.model.system.Branch

@Entity
@Table(name = "organizations")
data class Organization(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(nullable = false)
    var name: String,
    
    @Column(nullable = false)
    var inn: String,
    
    @Column(nullable = false)
    var kpp: String,
    
    @OneToMany(mappedBy = "organization", cascade = [CascadeType.ALL])
    var branches: MutableList<Branch> = mutableListOf(),
    
    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) 