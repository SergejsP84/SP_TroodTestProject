package com.sptest.project_SP_TroodTest.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Tag(name = "Profile", description = "Endpoints for managing user profiles")
annotation class GetProfileSwagger {
    companion object {
        const val SUMMARY = "Get Profile by ID"
        const val DESCRIPTION = "Retrieve a user's profile by their unique ID."
    }

    @Operation(
        summary = SUMMARY,
        description = DESCRIPTION,
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved the profile.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProfileResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Profile not found for the given user ID.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )]
            )
        ]
    )
    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class GetProfile
}

data class ProfileResponse(
    val name: String,
    val surname: String,
    val jobTitle: String,
    val phone: String,
    val address: String,
    val interests: List<String>,
    val isPublic: Boolean,
    val profileLink: String
)

data class ErrorResponse(
    val message: String
)