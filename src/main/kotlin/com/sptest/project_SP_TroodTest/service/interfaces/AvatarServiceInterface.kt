package com.sptest.project_SP_TroodTest.service.interfaces

import org.springframework.web.multipart.MultipartFile

interface AvatarServiceInterface {
    fun uploadAvatar(userId: Long, file: MultipartFile): String
}