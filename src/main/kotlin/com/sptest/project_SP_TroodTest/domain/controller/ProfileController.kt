package com.sptest.project_SP_TroodTest.domain.controller

import com.sptest.project_SP_TroodTest.domain.dto.ProfileDTO
import com.sptest.project_SP_TroodTest.domain.dto.SignUpDTO
import com.sptest.project_SP_TroodTest.exceptions.InvalidFieldException
import com.sptest.project_SP_TroodTest.exceptions.ProfileNotFoundException
import com.sptest.project_SP_TroodTest.service.implementation.ProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/profile")
class ProfileController(private val profileService: ProfileService) {

    @GetMapping("/{userId}")
    fun getProfile(@PathVariable userId: Long): ProfileDTO {
        return profileService.getProfile(userId)
    }

    @PutMapping("/{userId}/update")
    fun updateProfile(
        @PathVariable userId: Long,
        @RequestBody profileDTO: ProfileDTO
    ): ResponseEntity<Any> {
        return try {
            val updatedProfile = profileService.updateProfile(userId, profileDTO)
            ResponseEntity.ok(updatedProfile) // Return updated profile with status 200 OK
        } catch (e: ProfileNotFoundException) {
            // Return 404 NOT FOUND with error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf("error" to e.message))
        } catch (e: InvalidFieldException) {
            // Return 400 BAD REQUEST with error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(mapOf("error" to e.message))
        } catch (e: Exception) {
            // Return 500 INTERNAL SERVER ERROR with generic message for unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("error" to "An unexpected error occurred. Please try again later."))
        }
    }

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