package com.sptest.project_SP_TroodTest.service.interfaces

import com.sptest.project_SP_TroodTest.domain.dto.ProfileDTO
import org.springframework.security.core.Authentication
import org.springframework.web.multipart.MultipartFile

interface ProfileServiceInterface {
    fun getProfile(userId: Long, authentication: Authentication?): ProfileDTO
    fun updateProfile(userId: Long, profileDTO: ProfileDTO, authentication: Authentication): ProfileDTO
    fun uploadAvatar(userId: Long, file: MultipartFile, authentication: Authentication): String
}