package model

import cafe.adriel.voyager.core.screen.Screen
import kotlinx.datetime.LocalDate
import view.new_event.NewEventScreen
import view.shared.HelperFunctions
import view.shared.ListItem

class Participant(
    val allergies: List<String>,
    val birthdate: LocalDate?,
    val eatingHabit: String,
    val firstName: String,
    val lastName: String,
    var from: LocalDate,
    var to: LocalDate
)

fun Participant.toListItem(): ListItem<Participant> {
    return object : ListItem<Participant> {
        override fun getTitle(): String {
            return this@toListItem.firstName + " " + this@toListItem.lastName // Example: Mapping 'name' property to getTitle()
        }

        override fun getSubtitle(): String {
            return HelperFunctions.formatDate(this@toListItem.from) + " - " +  HelperFunctions.formatDate(this@toListItem.to)
        }

        override fun navigateTo(): Screen {
            TODO("Not yet implemented")
        }

        override fun getItem(): Participant {
            return this@toListItem
        }
    }
}