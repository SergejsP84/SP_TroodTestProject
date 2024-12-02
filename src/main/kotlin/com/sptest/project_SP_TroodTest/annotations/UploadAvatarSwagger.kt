package com.sptest.project_SP_TroodTest.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Avatar", description = "Endpoints for managing user avatars")
annotation class UploadAvatarSwagger {
    companion object {
        const val SUMMARY = "Upload or change avatar"
        const val DESCRIPTION = "Allows a user to upload or change their avatar image. Only images with .jpg, .jpeg, or .png extensions are allowed, with a size limit of 5 MB. Requires authorization as the respective user or as the Admin."
    }

    @Operation(
        summary = SUMMARY,
        description = DESCRIPTION,
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Avatar successfully uploaded.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = AvatarUploadResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid file type or file size exceeds the limit.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Unauthorized access - you cannot upload an avatar for another user.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Profile not found for the specified user ID.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Unexpected server error during avatar upload.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )]
            )
        ]
    )
    @RequestBody(
        description = "The avatar image to upload (JPEG or PNG file, max size: 5 MB).",
        required = true,
        content = [Content(
            mediaType = "multipart/form-data",
            schema = Schema(type = "string", format = "binary")
        )]
    )
    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class UploadAvatar
}

data class AvatarUploadResponse(
    val avatarUrl: String
)