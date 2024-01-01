package view.event.participants

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.datetime.LocalDate
import model.EatingHabit
import model.Event
import model.Participant

class ParticipantSearchBarScreen(private val event: Event): Screen {

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
    @Composable
    override fun Content() {
        ParticipantSearchBar(participants, event)
    }
}