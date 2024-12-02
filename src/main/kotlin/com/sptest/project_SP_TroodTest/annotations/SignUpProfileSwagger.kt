package com.sptest.project_SP_TroodTest.annotations

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Profile", description = "Endpoints for managing user profiles")
annotation class SignUpProfileSwagger {
    companion object {
        const val SUMMARY = "Sign up for a new profile"
        const val DESCRIPTION = "This endpoint allows a user to sign up by creating a new profile with their details."
    }

    @Operation(
        summary = SUMMARY,
        description = DESCRIPTION,
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Profile successfully created.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SuccessResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid field in the provided data.",
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
        description = "Sign-up information for creating a new profile.",
        required = true,
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = SignUpDTO::class)
        )]
    )
    @Target(AnnotationTarget.FUNCTION)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class SignUp
}

data class SignUpDTO(
    val name: String,
    val surname: String,
    val jobTitle: String,
    val phone: String,
    val address: String,
    val interests: List<String>,
    val isPublic: Boolean,
    val profileLink: String,
    val login: String,
    val password: String
)

data class SuccessResponse(
    val message: String
)
