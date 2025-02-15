package com.dev.bb.api.service

import com.dev.bb.api.model.system.Branch
import com.dev.bb.api.repository.system.BranchRepository
import org.springframework.stereotype.Service
import java.util.NoSuchElementException

@Service
class BranchService(private val branchRepository: BranchRepository) : IBranchService {

    override fun getAllBranches(): List<Branch> {
        return branchRepository.findAll()
    }

    override fun createBranch(branch: Branch): Branch {
        return branchRepository.save(branch)
    }

    override fun getBranchById(id: Long): Branch {
        return branchRepository.findById(id).orElseThrow { NoSuchElementException("Branch not found") }
    }
} 