package com.sptest.project_SP_TroodTest.service.implementation

import com.sptest.project_SP_TroodTest.exceptions.ProfileNotFoundException
import com.sptest.project_SP_TroodTest.repository.ProfileRepository
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class ProfileSecurityService(private val profileRepository: ProfileRepository) {

    // Check if the logged-in user is the profile owner or an admin
    fun isOwnerOrAdmin(authentication: Authentication, userId: Long): Boolean {
        val loggedInUsername = authentication.name
        val profile = profileRepository.findById(userId).orElseThrow {
            ProfileNotFoundException(userId)
        }
        // Check if the logged-in user is the owner or admin
        return loggedInUsername == profile.login || loggedInUsername == "admin"
    }
}