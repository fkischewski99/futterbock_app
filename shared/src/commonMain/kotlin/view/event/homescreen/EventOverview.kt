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
fun EventOverview(viewModelEventOverview: ViewModelEventOverview) {
    MaterialTheme {
        val state = viewModelEventOverview.state.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

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
                        state.value.upcommingEvents,
                        addItemToList = { navigator.push(NewEventScreen(null)) },
                        onDeleteClick = { viewModelEventOverview.onDeleteClick(it.getItem()) }
                    )
                    CardWithList("Vergangene Lager", state.value.pastEvents)
                }
            }
        }
    }


}
