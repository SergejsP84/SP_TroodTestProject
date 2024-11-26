package com.sptest.project_SP_TroodTest.domain.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            // Disable CSRF for simplicity (re-enable and configure as needed)
            .csrf { it.disable() }

            // Enable CORS
            .cors(Customizer.withDefaults())

            // Configure headers for security
            .headers { headers ->
                headers
                    .xssProtection { xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK) }
                    .contentSecurityPolicy { csp -> csp.policyDirectives("default-src 'self'; script-src 'self'") }
            }

            // Authorization rules
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers("/profile/signup").permitAll()
                    .requestMatchers("/profile/{userId}").permitAll()
                    .requestMatchers("/profile/{userId}/update").permitAll()
                    .requestMatchers("/upload-avatar/{userId}").permitAll()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll() // Swagger
                    .anyRequest().authenticated() // All other endpoints require auth
            }

            // Basic HTTP authentication (add JWT or other mechanisms as needed)
            .httpBasic(Customizer.withDefaults())

            // Disable form login (not needed for API-first project)
            .formLogin { it.disable() }

            // Logout configuration
            .logout { logout ->
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll()
            }

        return http.build()
    }
}