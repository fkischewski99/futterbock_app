package view.event.new_event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.atStartOfDayIn
import view.shared.DateRangePickerDialog
import view.shared.HelperFunctions
import kotlin.time.Duration.Companion.days

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
fun SimpleDateRangePickerInDatePickerDialog(
    onSelect: (selectedStartMilis: Long, selectedEndMilis: Long) -> Unit,
    from: LocalDate?,
    to: LocalDate?
) {

    fun getMillis(date: LocalDate?): Long {
        if(date != null){
            return date.atStartOfDayIn(kotlinx.datetime.TimeZone.currentSystemDefault()).toEpochMilliseconds()
        }
        return Clock.System.now().toEpochMilliseconds()
    }
    var showDatePicker by remember { mutableStateOf(false) }
    var dateStartMillis by remember { mutableStateOf(getMillis(from)) }
    var dateEndMillis by remember { mutableStateOf(getMillis(to)) }

    //onSelect(dateRangePickerState.selectedStartDateMillis!!, dateRangePickerState.selectedEndDateMillis!!);
    FlowRow (
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            value = "" + dateStartMillis?.let {
                HelperFunctions.formatLongDate(it)
            },
            label = { Text("Start:") },
            onValueChange = { },
            readOnly = true,
            enabled = true,
            modifier = Modifier
                .padding(8.dp).height(IntrinsicSize.Min)
        )
        OutlinedTextField(
            value = "" + dateEndMillis.let {
                HelperFunctions.formatLongDate(it)
            },
            label = { Text("Ende:") },
            onValueChange = { },
            readOnly = true,
            enabled = true,
            modifier = Modifier
                .padding(8.dp).height(IntrinsicSize.Min)
        )
        Button(
            // Calendar icon to open DatePicker
            onClick = {
                showDatePicker = true;
            },
            modifier = Modifier
                .padding(8.dp).height(IntrinsicSize.Min).align(Alignment.CenterVertically),

            ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Calendar",
            )
        }
    }
    if (showDatePicker) {
        DateRangePickerDialog(
            onSelect = { startMillis, endMillis ->
                onSelect(startMillis, endMillis)
                dateStartMillis = startMillis
                dateEndMillis = endMillis
                showDatePicker = false // Set value to false after onSelect is executed
            }
        )
    }
}
