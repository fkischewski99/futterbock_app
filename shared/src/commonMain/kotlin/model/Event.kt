package model

import cafe.adriel.voyager.core.screen.Screen
import view.shared.ListItem
import kotlinx.datetime.LocalDate
import view.event.new_event.NewEventScreen
import view.shared.HelperFunctions

class Event(
    val id: String = "",
    var from: LocalDate? = null,
    var to: LocalDate? = null,
    val eventType: String = "",
    val group: String = "",
    val kitchenSchedule: String = "",
    val meals: MutableList<Meal> = ArrayList(),
    var name: String = "",
    var participantsSchedule: List<Participant> = ArrayList()
): ListItem<Event> {
    override fun getTitle(): String {
        return this.name // Example: Mapping 'name' property to getTitle()
    }

    override fun getSubtitle(): String {
        if(from == null || to == null)
            return ""
        return HelperFunctions.formatDate(this.from!!) + " - " +  HelperFunctions.formatDate(this.to!!)
    }

    override fun navigateTo(): Screen {
        return NewEventScreen(this);
    }

    override fun getItem(): Event {
        return this
    }
}