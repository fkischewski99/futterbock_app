package model

import view.shared.ListItem
import java.time.LocalDate
data class Event(
    val from: LocalDate,
    val to: LocalDate,
    val eventType: String,
    val group: String,
    val kitchenSchedule: String,
    val meals: List<String>,
    val name: String,
    val participantsSchedule: List<ParticipantsSchedule>
)

// Extension function to map Event to ListItem
fun Event.toListItem(): ListItem {
    return object : ListItem {
        override fun getTitle(): String {
            return this@toListItem.name // Example: Mapping 'name' property to getTitle()
        }

        override fun getSubtitle(): String {
            return this@toListItem.from.toString() + " - " +  this@toListItem.to.toString()
        }

        override fun onClick(): String {
            return "Clicked ${this@toListItem.name}" // Example: Generating onClick behavior
        }
    }
}