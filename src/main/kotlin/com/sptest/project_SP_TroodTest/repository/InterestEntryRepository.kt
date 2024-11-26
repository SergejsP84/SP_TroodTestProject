package com.sptest.project_SP_TroodTest.repository

import com.sptest.project_SP_TroodTest.domain.entity.InterestEntry
import org.springframework.data.jpa.repository.JpaRepository

interface InterestEntryRepository : JpaRepository<InterestEntry, Long> {
    fun findByTitleIgnoreCase(title: String): InterestEntry?
}