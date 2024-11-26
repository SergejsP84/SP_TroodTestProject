package com.sptest.project_SP_TroodTest.service.implementation

import com.sptest.project_SP_TroodTest.service.interfaces.AvatarServiceInterface
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class AvatarService(@Value("\${avatar.storage.path:avatars}") private val avatarStoragePath: String) : AvatarServiceInterface {

    override fun uploadAvatar(userId: Long, file: MultipartFile): String {
        // Validate file extension
        val allowedExtensions = listOf("jpg", "jpeg", "png")
        val fileExtension = file.originalFilename?.split('.')?.lastOrNull()?.lowercase()
            ?: throw IllegalArgumentException("Invalid file: Missing file extension.")

        if (fileExtension !in allowedExtensions) {
            throw IllegalArgumentException("Invalid file type: Only .jpg, .jpeg, and .png are allowed.")
        }

        // Generate unique file name
        val fileName = "avatar_${userId}_${System.currentTimeMillis()}.$fileExtension"
        val targetPath = Paths.get(avatarStoragePath, fileName)

        // Save the file
        file.inputStream.use { input ->
            Files.copy(input, targetPath, StandardCopyOption.REPLACE_EXISTING)
        }

        // Return the relative URL
        return "avatars/$fileName"
    }
}