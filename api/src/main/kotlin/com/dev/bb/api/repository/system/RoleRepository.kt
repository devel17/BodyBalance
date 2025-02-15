package com.dev.bb.api.repository.system

import com.dev.bb.api.model.system.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(name: String): Role?
    fun findByNameContainingIgnoreCase(name: String): List<Role>
} 