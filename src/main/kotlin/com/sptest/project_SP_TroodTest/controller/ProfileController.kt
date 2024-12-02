package com.sptest.project_SP_TroodTest.controller

import com.sptest.project_SP_TroodTest.annotations.GetProfileSwagger
import com.sptest.project_SP_TroodTest.annotations.SignUpProfileSwagger
import com.sptest.project_SP_TroodTest.annotations.UpdateProfileSwagger
import com.sptest.project_SP_TroodTest.domain.dto.ProfileDTO
import com.sptest.project_SP_TroodTest.domain.dto.SignUpDTO
import com.sptest.project_SP_TroodTest.exceptions.InvalidFieldException
import com.sptest.project_SP_TroodTest.exceptions.ProfileNotFoundException
import com.sptest.project_SP_TroodTest.exceptions.UnauthorizedAccessException
import com.sptest.project_SP_TroodTest.service.implementation.ProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/profile")
class ProfileController(private val profileService: ProfileService) {

    @GetProfileSwagger.GetProfile
    @GetMapping("/{userId}")
    fun getProfile(@PathVariable userId: Long, authentication: Authentication?): ProfileDTO {
        return profileService.getProfile(userId, authentication)
    }

    @UpdateProfileSwagger.UpdateProfile
    @PutMapping("/{userId}/update")
    fun updateProfile(
        @PathVariable userId: Long,
        @RequestBody profileDTO: ProfileDTO,
        authentication: Authentication // Inject the Authentication object
    ): ResponseEntity<Any> {
        return try {
            val updatedProfile = profileService.updateProfile(userId, profileDTO, authentication) // Pass Authentication to the service method
            ResponseEntity.ok(updatedProfile) // Return updated profile with status 200 OK
        } catch (e: ProfileNotFoundException) {
            // Return 404 NOT FOUND with error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf("error" to e.message))
        } catch (e: InvalidFieldException) {
            // Return 400 BAD REQUEST with error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(mapOf("error" to e.message))
        } catch (e: UnauthorizedAccessException) {
            // Return 403 FORBIDDEN with error message
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(mapOf("error" to e.message))
        } catch (e: Exception) {
            // Return 500 INTERNAL SERVER ERROR with generic message for unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("error" to "An unexpected error occurred. Please try again later."))
        }
    }

    @SignUpProfileSwagger.SignUp
    @PostMapping("/signup")
    fun signUp(@RequestBody signUpDTO: SignUpDTO): ResponseEntity<String> {
        return try {
            profileService.signUp(signUpDTO)
            ResponseEntity.ok("Profile successfully created.")
        } catch (e: InvalidFieldException) {
            ResponseEntity.badRequest().body("Invalid field: ${e.fieldName}. Error: ${e.message}")
        } catch (e: Exception) {
            // Handle other exceptions
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during sign-up.")
        }
    }
}