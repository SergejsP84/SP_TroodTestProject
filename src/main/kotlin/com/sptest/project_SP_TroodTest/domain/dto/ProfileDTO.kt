package com.sptest.project_SP_TroodTest.domain.dto

data class ProfileDTO(
    val name: String,
    val surname: String,
    val jobTitle: String,
    val phone: String,
    val address: String,
    val interests: MutableList<String>,
    val isPublic: Boolean,
    val profileLink: String

)