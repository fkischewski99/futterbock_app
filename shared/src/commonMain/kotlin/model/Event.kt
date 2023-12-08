package model

data class Event(
    val date: String,
    val eventType: String,
    val group: String,
    val kitchenSchedule: String,
    val meals: List<String>,
    val name: String,
    val participantsSchedule: List<ParticipantsSchedule>
)