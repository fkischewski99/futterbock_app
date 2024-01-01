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
import model.Event
import model.toListItem
import kotlinx.datetime.LocalDate
import view.event.new_event.NewEventScreen
import view.shared.ListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventOverview() {
    MaterialTheme {
        val event = Event(
            "id",
            LocalDate.parse("2023-12-01"),
            LocalDate.parse("2023-12-05"),
            "stammeslager",
            "ab",
            "yoyo",
            ArrayList(),
            "ab",
            ArrayList()
        );
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
        var currentList by mutableStateOf(mutableStateListOf(event.toListItem()))
        var pastList by mutableStateOf(listOf(pastEvent.toListItem()))
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
