package view.event.homescreen

import CardWithList
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.datetime.Clock
import model.Event
import model.toListItem
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import model.EatingHabit
import model.Meal
import model.MealType
import model.Participant
import view.event.new_event.NewEventScreen
import view.event.new_meal_screen.ExampleObjects
import view.shared.ListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventOverview() {
    MaterialTheme {
        var meal = Meal(
            LocalDate.parse("2024-12-01"),
            MealType.MITTAG,
            ExampleObjects.getAllRecepies()
        )
        var meal2 = Meal(
            LocalDate.parse("2024-12-01"),
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
            LocalDate.parse("2024-12-01"),
            LocalDate.parse("2024-12-04"),
        );
        var participant2 = Participant(
            listOf(""),
            null,
            EatingHabit.OMNIVORE,
            "Fredö",
            "K",
            LocalDate.parse("2024-12-01"),
            LocalDate.parse("2024-12-04"),
        );
        val participants = mutableListOf(participant, participant2)
        val event = Event(
            "id",
            LocalDate.parse("2024-12-01"),
            LocalDate.parse("2024-12-04"),
            "Stamm",
            "Group",
            "kitchen",
            meals,
            "Lager 1",
            participants
        )
        val pastEvent = Event(
            "id",
            LocalDate.parse("2023-12-08"),
            LocalDate.parse("2023-12-10"),
            "stammeslager",
            "ab",
            "yoyo",
            ArrayList(),
            "ab",
            ArrayList()
        );

        // Create a mutable state for the list
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val allEvents = listOf(event, pastEvent)
        var currentList by mutableStateOf(mutableStateListOf<ListItem<Event>>()) // Events happening today or in the future
        var pastList by mutableStateOf(mutableStateListOf<ListItem<Event>>())    // Past events

        allEvents.forEach { event ->
            if (event.from == null){
                currentList.add(event.toListItem())
            }
            else if (event.from!! >= currentDate) {
                currentList.add(event.toListItem())
            } else {
                pastList.add(event.toListItem())
            }
        }

        val navigator = LocalNavigator.currentOrThrow

        fun deleteItemFromCurrentList(item: ListItem<Event>) {
            //TODO actually remove item
            currentList.remove(item);
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background),
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Lagerübersicht") }, // Title in the top bar
                    )
                }
            ) {
                Column(
                    modifier = Modifier.padding(top = 45.dp)
                ) {
                    // Your screen content

                    CardWithList(
                        "Aktuelle Lager",
                        currentList,
                        addItemToList = { navigator.push(NewEventScreen(null)) },
                        onDeleteClick = { deleteItemFromCurrentList(it) }
                    )
                    CardWithList("Vergangene Lager", pastList)
                }
            }
        }
    }


}
