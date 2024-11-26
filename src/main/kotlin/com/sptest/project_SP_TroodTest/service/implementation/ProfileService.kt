package com.sptest.project_SP_TroodTest.service.implementation

import com.sptest.project_SP_TroodTest.domain.dto.ProfileDTO
import com.sptest.project_SP_TroodTest.domain.dto.SignUpDTO
import com.sptest.project_SP_TroodTest.domain.entity.InterestEntry
import com.sptest.project_SP_TroodTest.domain.entity.Profile
import com.sptest.project_SP_TroodTest.domain.enums.ProfileVisibility
import com.sptest.project_SP_TroodTest.domain.mappers.ProfileMapper
import com.sptest.project_SP_TroodTest.exceptions.InvalidFieldException
import com.sptest.project_SP_TroodTest.exceptions.ProfileNotFoundException
import com.sptest.project_SP_TroodTest.repository.InterestEntryRepository
import com.sptest.project_SP_TroodTest.repository.ProfileRepository
import com.sptest.project_SP_TroodTest.service.interfaces.ProfileServiceInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Service
class ProfileService(
    private val profileRepository: ProfileRepository, // Profile repository to fetch/save data
    private val interestEntryRepository: InterestEntryRepository,
    private val avatarService: AvatarService // (Optional) A service to handle avatar uploads
) : ProfileServiceInterface {

    // Retrieve a user profile by ID
    override fun getProfile(userId: Long): ProfileDTO {
        val profile = profileRepository.findById(userId).orElseThrow {
            ProfileNotFoundException(userId)
        }
        return ProfileMapper().run { profile.toDTO() }
    }

    // Update the user profile with new data
    @Transactional
    override fun updateProfile(userId: Long, profileDTO: ProfileDTO): ProfileDTO {
        val profile = profileRepository.findById(userId).orElseThrow {
            ProfileNotFoundException(userId)
        }

        // Define censored roots
        val censoredRoots = listOf("fuck", "shit")

        // Temporary list for updated InterestEntry objects
        val updatedInterests = processInterests(profileDTO.interests, censoredRoots)

        // Validate fields
        validateProfileFields(profileDTO)

        // Update the profile fields
        profile.apply {
            name = profileDTO.name
            surname = profileDTO.surname
            jobTitle = profileDTO.jobTitle
            phone = profileDTO.phone
            address = profileDTO.address
            isPublic = if (profileDTO.isPublic) ProfileVisibility.PUBLIC else ProfileVisibility.PRIVATE
            interests = updatedInterests
        }

        profileRepository.save(profile)

        // Return the updated profile in DTO form
        return ProfileMapper().run { profile.toDTO() }

    }


    @Transactional
    fun signUp(signUpDTO: SignUpDTO) {
        // Validate all the fields from SignUpDTO
        validateName(signUpDTO.name)
        validateSurname(signUpDTO.surname)
        validateJobTitle(signUpDTO.jobTitle)
        validatePhone(signUpDTO.phone)
        validateAddress(signUpDTO.address)
        validateInterests(signUpDTO.interests)
        validateProfileLink(signUpDTO.profileLink)
        validateLogin(signUpDTO.login)
        validatePassword(signUpDTO.password)

        // Check if login or phone already exists in the database
        val existingUserByPhone = profileRepository.findByPhone(signUpDTO.phone)
        if (existingUserByPhone != null) throw InvalidFieldException("phone", "This phone number is already associated with an existing account.")
        val existingUserByLogin = profileRepository.findByLogin(signUpDTO.login)
        if (existingUserByLogin != null) throw InvalidFieldException("login", "This login is already taken.")

        // Define censored roots
        val censoredRoots = listOf("fuck", "shit")

        // Temporary list for updated InterestEntry objects
        val updatedInterests = processInterests(signUpDTO.interests, censoredRoots)

        // Encrypt the password
        val encoder = BCryptPasswordEncoder()
        val hashedPassword = encoder.encode(signUpDTO.password)

        // Create new profile from the DTO
        val newProfile = Profile(
            name = signUpDTO.name,
            surname = signUpDTO.surname,
            jobTitle = signUpDTO.jobTitle,
            phone = signUpDTO.phone,
            address = signUpDTO.address,
            interests = updatedInterests,
            isPublic = if (signUpDTO.isPublic) ProfileVisibility.PUBLIC else ProfileVisibility.PRIVATE,
            profileLink = signUpDTO.profileLink,
            login = signUpDTO.login,
            password = hashedPassword
        )

        // Save the new user profile
        profileRepository.save(newProfile)

        // WILL HANDLE LATER WITH ENOUGH TIME Optional: Send a welcome email or perform other post-sign-up actions
    }

    // Handle the avatar upload and save the URL
    @Transactional
    override fun uploadAvatar(userId: Long, file: MultipartFile): String {
        val fileUrl = avatarService.uploadAvatar(userId, file)

        val profile = profileRepository.findById(userId).orElseThrow {
            RuntimeException("Profile not found for user id $userId")
        }

        profile.avatarUrl = fileUrl
        profileRepository.save(profile)

        return fileUrl
    }

    // Auxiliary validation functions
    private fun validateProfileFields(profileDTO: ProfileDTO) {
        validateName(profileDTO.name)
        validateSurname(profileDTO.surname)
        validateJobTitle(profileDTO.jobTitle)
        validatePhone(profileDTO.phone)
        validateAddress(profileDTO.address)
        validateInterests(profileDTO.interests)
        validateProfileLink(profileDTO.profileLink)
    }

    // Validation Methods for Each Field
    fun validateName(name: String) {
        if (name.isBlank()) throw InvalidFieldException("name", "Name is required.")
        if (name.length < 2 || name.length > 50) throw InvalidFieldException("name", "Name must be between 2 and 50 characters.")
        if (!name.matches("^[A-Za-zА-Яа-яЁё\\s]+$".toRegex())) {
            throw InvalidFieldException("name", "Name may only contain letters and spaces.")
        }
    }

    fun validateSurname(surname: String) {
        if (surname.isBlank()) throw InvalidFieldException("surname", "Surname is required.")
        if (surname.length < 2 || surname.length > 50) throw InvalidFieldException("surname", "Surname must be between 2 and 50 characters.")
        if (!surname.matches("^[A-Za-zА-Яа-яЁё\\s]+$".toRegex())) {
            throw InvalidFieldException("surname", "Surname may only contain letters and spaces.")
        }
    }

    fun validateJobTitle(jobTitle: String) {
        if (jobTitle.length > 100) throw InvalidFieldException("jobTitle", "Job Title must not exceed 100 characters.")
        if (jobTitle.isNotBlank() && !jobTitle.matches("^[0-9A-Za-zА-Яа-яЁё\\s]+$".toRegex())) {
            throw InvalidFieldException("jobTitle", "Job Title may only contain letters, digits, and spaces.")
        }
    }

    fun validatePhone(phone: String) {
        if (phone.isBlank()) throw InvalidFieldException("phone", "Phone is required.")
        if (phone.length < 10 || phone.length > 15) throw InvalidFieldException("phone", "Phone must be between 10 and 15 characters.")
        if (!phone.matches("^\\+\\d{10,14}$".toRegex())) {
            throw InvalidFieldException("phone", "Phone must start with a '+' followed by 10 to 14 digits.")
        }
    }

    fun validateAddress(address: String) {
        if (address.length > 200) throw InvalidFieldException("address", "Address must not exceed 200 characters.")
        if (!address.matches("^[\\p{L}\\d,\\.\\-\\s]*$".toRegex())) {
            throw InvalidFieldException("address", "Address can only contain letters, digits, commas, periods, hyphens, and spaces.")
        }
    }

    fun validateInterests(interests: List<String>) {
        if (interests.size > 10) throw InvalidFieldException("interests", "You can only provide up to 10 interests.")
        interests.forEach { interest ->
            if (interest.length > 30) throw InvalidFieldException("interest", "Each interest must not exceed 30 characters.")
            if (!interest.matches("^[\\p{L}\\d\\s,\\.\\-:;\"'«»?!]*$".toRegex())) {
                throw InvalidFieldException("interest", "Interests can only contain letters, digits, spaces, commas, periods, hyphens, colons, semicolons, and punctuation marks.")
            }
        }
    }

    fun validateProfileLink(profileLink: String?) {
        profileLink?.let {
            if (it.length > 200) throw InvalidFieldException("profileLink", "Profile link must not exceed 200 characters.")
            if (!it.matches("^(http://|https://).*".toRegex())) {
                throw InvalidFieldException("profileLink", "Profile link must start with http:// or https://.")
            }
        }
    }

    fun validateAvatarUrl(avatarUrl: String?) {
        avatarUrl?.let {
            if (it.length > 200) throw InvalidFieldException("avatarUrl", "Avatar URL must not exceed 200 characters.")
            if (!it.matches(".*\\.(jpg|jpeg|png)$".toRegex())) {
                throw InvalidFieldException("avatarUrl", "Avatar URL must point to a .jpg, .jpeg, or .png file.")
            }
        }
    }

    fun validateLogin(login: String) {
        // Check if the login is not blank
        if (login.isBlank()) {
            throw InvalidFieldException("login", "Login cannot be blank")
        }

        // Validate the login pattern (Latin letters, digits, hyphens, or underscores)
        val loginPattern = "^[A-Za-z0-9_-]+$".toRegex()
        if (!login.matches(loginPattern)) {
            throw InvalidFieldException("login", "A login may only contain Latin letters, digits, hyphens, or underscores")
        }
    }

    fun validatePassword(password: String) {
        // Check if the password is not blank
        if (password.isBlank()) {
            throw InvalidFieldException("password", "Password cannot be blank")
        }

        // Check password length (minimum 8 characters)
        if (password.length < 8) {
            throw InvalidFieldException("password", "Password must be at least 8 characters long")
        }

        // Validate the password pattern (at least one lowercase, one uppercase, and one digit)
        val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$".toRegex()
        if (!password.matches(passwordPattern)) {
            throw InvalidFieldException("password", "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
        }
    }

    // Auxiliary method for processing them Interests
    fun processInterests(interestTitles: List<String>, censoredRoots: List<String>): MutableList<InterestEntry> {
        val updatedInterests = mutableListOf<InterestEntry>()

        interestTitles.forEach { interestTitle ->
            val sanitizedTitle = interestTitle.trim()

            // Check if any censored root is present in the title
            if (censoredRoots.any { sanitizedTitle.lowercase().contains(it) }) {
                println("Skipping censored interest: $sanitizedTitle")
                return@forEach
            }

            // Find existing InterestEntry or create a new one
            val interestEntry = interestEntryRepository.findByTitleIgnoreCase(sanitizedTitle)
                ?: InterestEntry(title = sanitizedTitle).apply { interestEntryRepository.save(this) }
            updatedInterests.add(interestEntry)
        }

        return updatedInterests
    }
}
