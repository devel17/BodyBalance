package com.dev.bb.api.repository.system

import com.dev.bb.api.model.system.Organization
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrganizationRepository : JpaRepository<Organization, Long> {
    fun findByInn(inn: String): Organization?
    fun findByName(name: String): List<Organization>
} 