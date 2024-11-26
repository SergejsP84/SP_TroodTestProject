package com.sptest.project_SP_TroodTest.domain.entity
import jakarta.persistence.*
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@Entity
@Table(name = "interest_entries")
@AllArgsConstructor
@NoArgsConstructor
data class InterestEntry(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:Size(max = 30)
    @field:Pattern(
        regexp = "^[\\p{L}\\d\\s,\\.\\-:;\"'«»?!]*\$",
        message = "Only letters, digits, spaces, commas, dots, hyphens, colons, semicolons, quotation marks (English and Russian), question marks, and exclamation marks are allowed."
    )
    val title: String,

    @ManyToMany(mappedBy = "interests")
    val profiles: List<Profile> = mutableListOf()
)