package view.event.participants

import CardWithList
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
        if(currentParticipant == null)
            return
        val startDateSelect = Instant.fromEpochMilliseconds(start)
        val endDateSelect = Instant.fromEpochMilliseconds(end)
        currentParticipant!!.from = startDateSelect.toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).date;
        currentParticipant!!.to = endDateSelect.toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).date;
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
    ) {

        Column(modifier = Modifier.padding(8.dp).verticalScroll(rememberScrollState())) {
            CardWithList(
                title = "Teilnehmer",
                listItems = currentList,
                onListItemClick = { setDatePickerActiveForItem(it) },
                addItemToList = {navigator.push(ParticipantSearchBarScreen(event))}

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