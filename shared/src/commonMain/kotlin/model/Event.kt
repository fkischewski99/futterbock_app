package model

import cafe.adriel.voyager.core.screen.Screen
import view.shared.ListItem
import kotlinx.datetime.LocalDate
import view.event.new_event.NewEventScreen
import view.shared.HelperFunctions

data class Event(
    val id: String,
    var from: LocalDate?,
    var to: LocalDate?,
    val eventType: String,
    val group: String,
    val kitchenSchedule: String,
    val meals: MutableList<Meal>,
    var name: String,
    var participantsSchedule: List<Participant>
)

// Extension function to map Event to ListItem
fun Event.toListItem(): ListItem<Event> {
    return object : ListItem<Event> {
        override fun getTitle(): String {
            return this@toListItem.name // Example: Mapping 'name' property to getTitle()
        }

        override fun getSubtitle(): String {
            if(from == null || to == null)
                return ""
            return HelperFunctions.formatDate(this@toListItem.from!!) + " - " +  HelperFunctions.formatDate(this@toListItem.to!!)
        }

        override fun navigateTo(): Screen {
            return NewEventScreen(this@toListItem);
        }

        override fun getItem(): Event {
            return this@toListItem
        }
    }
}