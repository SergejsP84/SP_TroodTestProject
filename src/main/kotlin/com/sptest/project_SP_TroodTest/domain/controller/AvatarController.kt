package com.sptest.project_SP_TroodTest.domain.controller

import com.sptest.project_SP_TroodTest.exceptions.ProfileNotFoundException
import com.sptest.project_SP_TroodTest.service.implementation.ProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/upload-avatar")
class AvatarController(private val profileService: ProfileService) {

    companion object {
        const val MAX_FILE_SIZE = 5 * 1024 * 1024 // 5 MB
        val ALLOWED_MIME_TYPES = listOf("image/jpeg", "image/png")
    }

    @PostMapping("/{userId}")
    fun uploadAvatar(@PathVariable userId: Long, @RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        return try {
            // Validate file size
            if (file.size > MAX_FILE_SIZE) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("File size exceeds the 5 MB limit.")
            }

            // Validate file type
            if (!ALLOWED_MIME_TYPES.contains(file.contentType)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Unsupported file type. Only .jpg, .jpeg, and .png are allowed.")
            }

            // Call service to handle file saving
            val avatarUrl = profileService.uploadAvatar(userId, file)
            ResponseEntity.ok(avatarUrl)
        } catch (e: ProfileNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Profile not found for user ID: $userId")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.message)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred while uploading the avatar.")
        }
    }
}