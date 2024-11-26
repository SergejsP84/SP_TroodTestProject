package com.sptest.project_SP_TroodTest.domain.mappers

import com.sptest.project_SP_TroodTest.domain.dto.ProfileDTO
import com.sptest.project_SP_TroodTest.domain.entity.Profile
import com.sptest.project_SP_TroodTest.domain.enums.ProfileVisibility

class ProfileMapper {
    fun Profile.toDTO(): ProfileDTO {
        return ProfileDTO(
            name = this.name,
            surname = this.surname,
            jobTitle = this.jobTitle,
            phone = this.phone,
            address = this.address,
            interests = this.interests.map { it.title }.toMutableList(),
            isPublic = this.isPublic == ProfileVisibility.PUBLIC,
            profileLink = (this.profileLink ?: "") as String
        )
    }
}
