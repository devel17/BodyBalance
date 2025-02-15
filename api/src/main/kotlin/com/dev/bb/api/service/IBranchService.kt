package com.dev.bb.api.service

import com.dev.bb.api.model.system.Branch

interface IBranchService {
    fun getAllBranches(): List<Branch>
    fun createBranch(branch: Branch): Branch
    fun getBranchById(id: Long): Branch
} 