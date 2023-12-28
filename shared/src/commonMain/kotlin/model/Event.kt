package model

import cafe.adriel.voyager.core.screen.Screen
import view.shared.ListItem
import kotlinx.datetime.LocalDate
import view.new_event.NewEventScreen

data class Event(
    val id: String,
    var from: LocalDate?,
    var to: LocalDate?,
    val eventType: String,
    val group: String,
    val kitchenSchedule: String,
    val meals: List<Meal>,
    val name: String,
    val participantsSchedule: List<Participant>
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

        override fun navigateTo(): Screen {
            return NewEventScreen(this@toListItem.id);
        }
    }
}