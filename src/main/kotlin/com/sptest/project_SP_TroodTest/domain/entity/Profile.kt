package com.sptest.project_SP_TroodTest.domain.entity

import com.sptest.project_SP_TroodTest.domain.enums.ProfileVisibility
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull
import jakarta.validation.constraints.Pattern
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@Entity
@Table(name = "profiles")
@AllArgsConstructor
@NoArgsConstructor
class Profile(

    @Id
    @field:NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank
    @field:Size(min = 2, max = 50) // all MIN and MAX value ranges assumed as inclusive
    @field:Pattern(regexp = "^[A-Za-zА-Яа-яЁё\\s]+$", message = "A name may only contain letters and spaces")
    // I really felt like allowing hyphens here as well, as per the example, but the instructions forbid that
    var name: String,

    @field:NotBlank
    @field:Size(min = 2, max = 50)
    @field:Pattern(regexp = "^[A-Za-zА-Яа-яЁё\\s]+$", message = "A surname may only contain letters and spaces")
    // Same as above
    var surname: String,

    @field:Size(max = 100)
    @field:Pattern(regexp = "^[0-9A-Za-zА-Яа-яЁё\\s]+$", message = "A job title may only contain letters, spaces, hyphens or digits")
    var jobTitle: String = "",

    @field:Size(min = 10, max = 15)
    @field:Pattern(
        regexp = "^\\+\\d{10,14}$",
        message = "Phone must start with a '+' followed by 10 to 14 digits."
    )
    @field:NotBlank
    var phone: String,

    @field:Size(max = 200)
    @field:Pattern(
        regexp = "^[\\p{L}\\d,\\.\\-\\s]*\$",
        message = "Only letters, digits, commas, dots, hyphens, and spaces are allowed."
    )
    var address: String = "",

    @ManyToMany
    @JoinTable(
        name = "profile_interest_entries",
        joinColumns = [JoinColumn(name = "profile_id")],
        inverseJoinColumns = [JoinColumn(name = "interest_entry_id")]
    )
    var interests: MutableList<InterestEntry> = mutableListOf(),

    var isPublic: ProfileVisibility = ProfileVisibility.PRIVATE,

    @field:Size(max = 200)
    @field:Pattern(
        regexp = "^(http://|https://).*",
        message = "URL must start with http:// or https://"
    )
    var profileLink: String? = null,

    @field:Pattern(
        regexp = ".*\\.(jpg|jpeg|png)$",
        message = "Avatar must point to a file with one of the supported formats: .jpg, .jpeg, or .png"
    )
    var avatarUrl: String? = null,

    @NotBlank
    @field:Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "A login may only contain Latin letters, digits, hyphens, or underscores")
    var login: String,

    @NotBlank
    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
    var password: String,
)
