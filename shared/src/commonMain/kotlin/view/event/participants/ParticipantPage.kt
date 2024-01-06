package view.event.participants

import CardWithList
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.datetime.Instant
import kotlinx.datetime.toLocalDateTime
import model.Event
import model.Participant
import model.toListItem
import view.shared.DateRangePickerDialog
import view.shared.ListItem
import view.shared.NavigationIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticipantPage(event: Event) {
    var currentList by mutableStateOf(event.participantsSchedule.map { it.toListItem() })
    var showDatePicker by remember { mutableStateOf(false) }
    var currentParticipant: Participant? = null;
    val navigator = LocalNavigator.currentOrThrow

    fun setDatePickerActiveForItem(listItem: ListItem<Participant>) {
        showDatePicker = true;
        currentParticipant = listItem.getItem();
    }

    fun setFromToOfPatient(start: Long, end: Long) {
        if (currentParticipant == null)
            return
        val startDateSelect = Instant.fromEpochMilliseconds(start)
        val endDateSelect = Instant.fromEpochMilliseconds(end)
        currentParticipant!!.from =
            startDateSelect.toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).date;
        currentParticipant!!.to =
            endDateSelect.toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).date;
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Teilnehmerliste") },
                navigationIcon = { NavigationIconButton() }
            )
        }
    ) {

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = it.calculateTopPadding())
                .background(MaterialTheme.colorScheme.background),
        ) {

            Column(modifier = Modifier.padding(8.dp).verticalScroll(rememberScrollState())) {
                CardWithList(
                    title = "Teilnehmer",
                    listItems = currentList,
                    onListItemClick = { setDatePickerActiveForItem(it) },
                    addItemToList = { navigator.push(ParticipantSearchBarScreen(event)) },
                    onDeleteClick = {}

                )
            }
            if (showDatePicker) {
                DateRangePickerDialog(
                    onSelect = { startMillis, endMillis ->
                        setFromToOfPatient(startMillis, endMillis)
                        showDatePicker = false // Set value to false after onSelect is executed
                    }
                )
            }


        }
    }
}