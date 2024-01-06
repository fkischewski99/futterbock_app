package view.event.new_event

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.datetime.LocalDate
import model.EatingHabit
import model.Event
import model.Meal
import model.MealType
import model.Participant
import view.event.new_meal_screen.ExampleObjects

class NewEventScreen(private var event: Event?) : Screen {


    fun createNewEvent(): Event {
        return Event("id", null, null, "Stamm", "Group", "kitchen", ArrayList(), "", ArrayList())
    }

    fun getEventById(eventId: String): Event {
        var meal = Meal(
            LocalDate.parse("2023-12-01"),
            MealType.MITTAG,
            ExampleObjects.getAllRecepies()
        )
        var meal2 = Meal(
            LocalDate.parse("2023-12-03"),
            MealType.MITTAG,
            ExampleObjects.getAllRecepies()
        )
        var meals = mutableListOf(meal, meal2);

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
        val participants = mutableListOf(participant, participant2)
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
        if(event == null){
            event = createNewEvent()
        }
        NewEventPage(event!!)
    }
}