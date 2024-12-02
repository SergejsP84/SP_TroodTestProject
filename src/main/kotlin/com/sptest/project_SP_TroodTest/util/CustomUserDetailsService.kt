package com.sptest.project_SP_TroodTest.util

import com.sptest.project_SP_TroodTest.repository.ProfileRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userRepository: ProfileRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByLogin(username)
            ?: throw UsernameNotFoundException("User not found: $username")

        // Check if the user is "admin" based on login
        val role = if (user.login == "admin") "ADMIN" else "USER"
        val authorities = listOf(SimpleGrantedAuthority("ROLE_$role"))
        return User(user.login, user.password, authorities)
    }
}