package model

import kotlinx.datetime.LocalDate

data class Participant(
    val allergies: List<String>,
    val birthdate: LocalDate?,
    val eatingHabit: String,
    val firstName: String,
    val lastName: String,
    val from: LocalDate,
    val to: LocalDate
)