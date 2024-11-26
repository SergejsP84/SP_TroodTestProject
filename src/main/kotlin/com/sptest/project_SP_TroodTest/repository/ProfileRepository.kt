package com.sptest.project_SP_TroodTest.repository

import com.sptest.project_SP_TroodTest.domain.entity.Profile
import org.springframework.data.jpa.repository.JpaRepository

interface ProfileRepository : JpaRepository<Profile, Long> {
    fun findByPhone(phone: String): Profile?
    fun findByLogin(login: String): Profile?
}