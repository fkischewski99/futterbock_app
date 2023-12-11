package view.homescreen

import CardWithList
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.Event
import model.toListItem
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventOverview() {
    MaterialTheme {
        val event = Event( LocalDate.of(2023, 12, 1),  LocalDate.of(2023, 12, 5), "stammeslager", "ab", "yoyo", ArrayList(), "ab", ArrayList());
        val pastEvent = Event( LocalDate.of(2023, 12, 8),  LocalDate.of(2023, 12, 10), "stammeslager", "ab", "yoyo", ArrayList(), "ab", ArrayList());
        var currentList by remember { mutableStateOf(listOf(event.toListItem())) }
        var pastList by remember { mutableStateOf(listOf(pastEvent.toListItem())) }


        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Lagerübersicht") }, // Title in the top bar
                )
            }
        ) {
            Column (
                modifier = Modifier.padding(top = 45.dp)
            ){
                // Your screen content

                CardWithList("Aktuelle Lager", currentList)
                CardWithList("Vergangene Lager", pastList)
            }
        }
    }


}