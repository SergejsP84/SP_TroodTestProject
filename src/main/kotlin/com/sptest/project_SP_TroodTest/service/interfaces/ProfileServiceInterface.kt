package com.sptest.project_SP_TroodTest.service.interfaces

import com.sptest.project_SP_TroodTest.domain.dto.ProfileDTO
import org.springframework.web.multipart.MultipartFile

interface ProfileServiceInterface {
    fun getProfile(userId: Long): ProfileDTO
    fun updateProfile(userId: Long, profileDTO: ProfileDTO): ProfileDTO
    fun uploadAvatar(userId: Long, file: MultipartFile): String
}