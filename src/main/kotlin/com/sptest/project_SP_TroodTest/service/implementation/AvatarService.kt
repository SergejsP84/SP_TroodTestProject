package com.sptest.project_SP_TroodTest.service.implementation

import com.sptest.project_SP_TroodTest.exceptions.ProfileNotFoundException
import com.sptest.project_SP_TroodTest.exceptions.UnauthorizedAccessException
import com.sptest.project_SP_TroodTest.repository.ProfileRepository
import com.sptest.project_SP_TroodTest.service.interfaces.AvatarServiceInterface
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class AvatarService(@Value("\${avatar.storage.path:avatars}") private val avatarStoragePath: String, private val profileRepository: ProfileRepository) : AvatarServiceInterface {

    override fun uploadAvatar(userId: Long, file: MultipartFile, authentication: Authentication): String {
        // Get the logged-in user's login (Principal)
        val loggedInUsername = authentication.name

        // Get the profile by userId
        val profile = profileRepository.findById(userId).orElseThrow {
            ProfileNotFoundException(userId)
        }

        // Check if the logged-in user is either the profile owner or an admin
        if (loggedInUsername != profile.login && loggedInUsername != "admin") {
            throw UnauthorizedAccessException("You do not have permission to change this avatar.")
        }

        // If the user has a previous avatar, delete it
        profile.avatarUrl?.let {
            val previousAvatarPath = Paths.get(avatarStoragePath, it)
            if (Files.exists(previousAvatarPath)) {
                try {
                    Files.delete(previousAvatarPath) // Delete the previous avatar
                } catch (e: IOException) {
                    throw RuntimeException("Failed to delete previous avatar file.")
                }
            }
        }

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

        // Save the new avatar file
        file.inputStream.use { input ->
            Files.copy(input, targetPath, StandardCopyOption.REPLACE_EXISTING)
        }

        // Update the profile with the new avatar URL
        profile.avatarUrl = "avatars/$fileName"
        profileRepository.save(profile)

        // Return the relative URL for the new avatar
        return "avatars/$fileName"
    }
}