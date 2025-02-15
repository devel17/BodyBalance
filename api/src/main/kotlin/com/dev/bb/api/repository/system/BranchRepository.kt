package com.dev.bb.api.repository.system

import com.dev.bb.api.model.system.Branch
import com.dev.bb.api.model.system.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BranchRepository : JpaRepository<Branch, Long> {
    // Add any custom query methods if needed
}