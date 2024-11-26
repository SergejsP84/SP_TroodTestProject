package com.sptest.project_SP_TroodTest.domain.dto

data class SignUpDTO(
    val name: String,
    val surname: String,
    val jobTitle: String = "",
    val phone: String,
    val address: String = "",
    val interests: MutableList<String> = mutableListOf(),
    val isPublic: Boolean,
    val profileLink: String? = null,
    val login: String,
    val password: String

)