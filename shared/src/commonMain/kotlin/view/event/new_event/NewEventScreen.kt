package view.event.new_event

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.datetime.LocalDate
import model.EatingHabit
import model.Event
import model.Meal
import model.Participant
import model.RecipeSelection

class NewEventScreen(private val eventId: String?) : Screen {


    fun createNewEvent(): Event {
        return Event("id", null, null, "Stamm", "Group", "kitchen", ArrayList(), "", ArrayList())
    }

    fun getEventById(eventId: String): Event {
        var meal = Meal(
            LocalDate.parse("2023-12-01"),
            "Mittag",
            listOf(RecipeSelection("ab", ArrayList(), "Rezept 1"))
        )
        var meal2 = Meal(
            LocalDate.parse("2023-12-03"),
            "Abend",
            listOf(RecipeSelection("ab", ArrayList(), "Rezept 1"))
        )
        var meals = listOf(meal, meal2);

        var participant = Participant(
            listOf("Kiwi"),
            null,
            EatingHabit.VEGETARISCH,
            "Ronja",
            "Wehmeyer",
            LocalDate.parse("2023-12-01"),
            LocalDate.parse("2023-12-04")
        );
        var participant2 = Participant(
            listOf(""),
            null,
            EatingHabit.OMNIVORE,
            "Fredö",
            "K",
            LocalDate.parse("2023-12-01"),
            LocalDate.parse("2023-12-03")
        );
        val participants = listOf(participant, participant2)
        return Event(
            "id",
            LocalDate.parse("2023-12-01"),
            LocalDate.parse("2023-12-04"),
            "Stamm",
            "Group",
            "kitchen",
            meals,
            "Lager 1",
            participants
        )
    }

    @Composable
    override fun Content() {
        val event: Event;
        if(eventId != null){
            event = getEventById(eventId)
        }else{
            event = createNewEvent()
        }
        NewEventPage(event)
    }
}