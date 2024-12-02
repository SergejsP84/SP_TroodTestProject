package com.sptest.project_SP_TroodTest.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Profile", description = "Endpoints for managing user profiles")
annotation class UpdateProfileSwagger {
    companion object {
        const val SUMMARY = "Update Profile"
        const val DESCRIPTION = "Update a user's profile. This operation requires authentication and authorization. A user can update their own profile only; all profiles can also be edited by the Admin."
    }

    @Operation(
        summary = SUMMARY,
        description = DESCRIPTION,
        security = [SecurityRequirement(name = "BearerAuth")], // Specifies authorization requirement
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Profile successfully updated.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProfileDTO::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid field value(s) in the request body.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Forbidden: Unauthorized access to update the profile.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Profile not found for the given user ID.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Unexpected server error.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )]
            )
        ]
    )
    @RequestBody(
        description = "Profile data to update.",
        required = true,
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = ProfileDTO::class)
        )]
    )
    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class UpdateProfile
}

data class ProfileDTO(
    val name: String,
    val surname: String,
    val jobTitle: String,
    val phone: String,
    val address: String,
    val interests: List<String>,
    val isPublic: Boolean,
    val profileLink: String
)


