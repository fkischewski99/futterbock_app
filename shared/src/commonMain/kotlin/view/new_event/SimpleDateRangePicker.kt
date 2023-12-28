package view.new_event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.days

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
fun SimpleDateRangePickerInDatePickerDialog(onSelect: ((selectedStartMilis: Long, selectedEndMilis: Long) -> Unit)) {
    var showDatePicker by remember { mutableStateOf(false) }
    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = Clock.System.now().toEpochMilliseconds(),
        initialSelectedEndDateMillis = Clock.System.now().plus(4.days).toEpochMilliseconds(),
        yearRange = IntRange(2023, 2100),
        initialDisplayMode = DisplayMode.Picker
    )

    fun formatDate(time: Long): String {
        val date = Instant.fromEpochMilliseconds(time)
            .toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).date
        return "" + date.dayOfMonth + "." + date.monthNumber + "." + date.year;
    }
    //onSelect(dateRangePickerState.selectedStartDateMillis!!, dateRangePickerState.selectedEndDateMillis!!);
    FlowRow (
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            value = "" + dateRangePickerState.selectedStartDateMillis?.let {
                formatDate(it)
            },
            label = { Text("Start:") },
            onValueChange = { },
            readOnly = true,
            enabled = true,
            modifier = Modifier
                .padding(8.dp).height(IntrinsicSize.Min)
        )
        OutlinedTextField(
            value = "" + dateRangePickerState.selectedEndDateMillis?.let {
                formatDate(it)
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
                showDatePicker = true
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
        DatePickerDialog(
            shape = RoundedCornerShape(6.dp),
            onDismissRequest = {},
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    //Call function only if correct dates are selected
                    if (dateRangePickerState.selectedStartDateMillis != null && dateRangePickerState.selectedEndDateMillis != null) {
                        onSelect(
                            dateRangePickerState.selectedStartDateMillis!!,
                            dateRangePickerState.selectedEndDateMillis!!
                        )
                    }

                    //selectedDate = datePickerState.selectedDateMillis!!
                }) {
                    Text(text = "Bestätigen")
                }
            },
        ) {

            DateRangePicker(
                modifier = Modifier.weight(1f), // Important to display the button
                state = dateRangePickerState,
            )

        }
    }
}
