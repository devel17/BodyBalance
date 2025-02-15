package com.dev.bb.api.repo

import com.dev.bb.model.Profile
import org.springframework.data.repository.CrudRepository

interface ProfileRepository: CrudRepository<Profile, Long> {
}