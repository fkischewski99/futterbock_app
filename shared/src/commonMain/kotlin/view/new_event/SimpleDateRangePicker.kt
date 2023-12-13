package view.new_event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.days

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SimpleDateRangePickerInDatePickerDialog(
    openDialog: Boolean,
    onDismiss: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = Clock.System.now().toEpochMilliseconds(),
        initialSelectedEndDateMillis = Clock.System.now().plus(4.days).toEpochMilliseconds(),
        yearRange = IntRange(2023, 2100),
        initialDisplayMode = DisplayMode.Picker
    )
    Column(
        modifier = Modifier.padding(vertical = 10.dp, horizontal = 0.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End
    ) {
        Button(
            onClick = {
                showDatePicker = true
            }
        ) {
            Text(text = "Bearbeiten")
        }

        Text(
            text = "Start: ${dateRangePickerState.selectedEndDateMillis})}"
        )
        if (showDatePicker) {
            DatePickerDialog(
                shape = RoundedCornerShape(6.dp),
                onDismissRequest = onDismiss,
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        //selectedDate = datePickerState.selectedDateMillis!!
                    }) {
                        Text(text = "Confirm")
                    }
                },
            ) {

                DateRangePicker(
                    modifier = Modifier.weight(1f), // Important to display the button
                    state = dateRangePickerState,
                )

                TextButton(
                    onClick = {
                        onDismiss()
//                val startDate = dateRangePickerState.selectedStartDateMillis)
//                val startDate = dateRangePickerState.selectedStartDateMillis)
//                val endDate = dateRangePickerState.selectedEndDateMillis
                    },
                    enabled = dateRangePickerState.selectedEndDateMillis != null
                ) {
                    Text(text = "Validieren")
                }
            }
        }
    }
}