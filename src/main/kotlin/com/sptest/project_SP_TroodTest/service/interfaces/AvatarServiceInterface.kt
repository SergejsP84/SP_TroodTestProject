package com.sptest.project_SP_TroodTest.service.interfaces

import org.springframework.security.core.Authentication
import org.springframework.web.multipart.MultipartFile

interface AvatarServiceInterface {
    fun uploadAvatar(userId: Long, file: MultipartFile, authentication: Authentication): String
}