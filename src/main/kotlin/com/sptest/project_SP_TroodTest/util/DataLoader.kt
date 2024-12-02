package com.sptest.project_SP_TroodTest.util

import com.sptest.project_SP_TroodTest.domain.entity.InterestEntry
import com.sptest.project_SP_TroodTest.domain.entity.Profile
import com.sptest.project_SP_TroodTest.domain.enums.ProfileVisibility
import com.sptest.project_SP_TroodTest.repository.InterestEntryRepository
import com.sptest.project_SP_TroodTest.repository.ProfileRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Component
@Transactional
class DataLoader(
    private val profileRepository: ProfileRepository,
    private val interestRepository: InterestEntryRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        // Check if the database is empty
        if (profileRepository.count() == 0L) {
            val politics = interestRepository.save(InterestEntry(title = "Politics"))
            val sports = interestRepository.save(InterestEntry(title = "Sports"))
            val videoGames = interestRepository.save(InterestEntry(title = "Video Games"))
            val music = interestRepository.save(InterestEntry(title = "Music"))
            val science = interestRepository.save(InterestEntry(title = "Science"))
            val comedy = interestRepository.save(InterestEntry(title = "Comedy"))
            val art = interestRepository.save(InterestEntry(title = "Art"))
            val environment = interestRepository.save(InterestEntry(title = "Environment"))
            val cooking = interestRepository.save(InterestEntry(title = "Cooking"))
            val history = interestRepository.save(InterestEntry(title = "History"))
            val technology = interestRepository.save(InterestEntry(title = "Technology"))
            val philosophy = interestRepository.save(InterestEntry(title = "Philosophy"))

            // Creating profiles and assigning interests
            val kennyPassword = bCryptPasswordEncoder.encode("CheatingDeath13")
            val ericPassword = bCryptPasswordEncoder.encode("RespectMyAuth88")
            val stanPassword = bCryptPasswordEncoder.encode("LoveWendy00")
            val kylePassword = bCryptPasswordEncoder.encode("GreenHat74")
            val adminPassword = bCryptPasswordEncoder.encode("AdminPassword123")

            val baseAvatarUrl = "avatars/"

            val kennyAvatarUrl = "$baseAvatarUrl" + "KennyMcCormick.png"
            val stanAvatarUrl = "$baseAvatarUrl" + "StanMarsh.png"
            val kyleAvatarUrl = "$baseAvatarUrl" + "KyleBroflovski.png"
            val cartmanAvatarUrl = "$baseAvatarUrl" + "EricCartman.png"
            val adminAvatarUrl = "$baseAvatarUrl" + "Admin.png"

            val kenny = Profile(
                name = "Kenny",
                surname = "McCormick",
                phone = "+12223334445",
                address = "123 South Park St.",
                jobTitle = "Team Lead",
                interests = mutableListOf(politics, sports, videoGames, music),
                isPublic = ProfileVisibility.PUBLIC, // MADE PUBLIC FOR TEST PURPOSES
                profileLink = "https://en.wikipedia.org/wiki/Kenny_McCormick",
                avatarUrl = kennyAvatarUrl,
                login = "kenny",
                password = kennyPassword
            )

            val eric = Profile(
                name = "Eric",
                surname = "Cartman",
                phone = "+17778889999",
                address = "456 South Park St.",
                jobTitle = "Tax Inspector",
                interests = mutableListOf(comedy, politics, sports),
                isPublic = ProfileVisibility.PUBLIC, // MADE PUBLIC FOR TEST PURPOSES
                profileLink = "https://en.wikipedia.org/wiki/Eric_Cartman",
                avatarUrl = cartmanAvatarUrl,
                login = "eric",
                password = ericPassword
            )

            val stan = Profile(
                name = "Stan",
                surname = "Marsh",
                phone = "+15556667777",
                address = "789 South Park St.",
                jobTitle = "Student",
                interests = mutableListOf(music, science, sports),
                isPublic = ProfileVisibility.PRIVATE,
                profileLink = "https://en.wikipedia.org/wiki/Stan_Marsh",
                avatarUrl = stanAvatarUrl,
                login = "stan",
                password = stanPassword
            )

            val kyle = Profile(
                name = "Kyle",
                surname = "Broflovski",
                phone = "+16667778888",
                address = "1010 South Park St.",
                jobTitle = "Developer",
                interests = mutableListOf(history, science, videoGames),
                isPublic = ProfileVisibility.PRIVATE,
                profileLink = "https://en.wikipedia.org/wiki/Kyle_Broflovski",
                avatarUrl = kyleAvatarUrl,
                login = "kyle",
                password = kylePassword
            )

            val admin = Profile(
                name = "Admin",
                surname = "User",
                phone = "+37129242695",
                address = "123 Admin St.",
                jobTitle = "Administrator",
                interests = mutableListOf(technology, art, philosophy),
                isPublic = ProfileVisibility.PRIVATE,
                profileLink = "http://www.emendatus.lv",
                avatarUrl = adminAvatarUrl,
                login = "admin",
                password = adminPassword
            )

            // Saving profiles to the database
            profileRepository.saveAll(listOf(kenny, eric, stan, kyle, admin))

            println("Test data populated successfully.")
            } else {
            println("Database already contains data. Skipping test data population.")
            }
        }
    }
