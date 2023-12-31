package view.shared

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.days

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerDialog(
 onSelect: ((selectedStartMilis: Long, selectedEndMilis: Long) -> Unit)
) {
    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = Clock.System.now().toEpochMilliseconds(),
        initialSelectedEndDateMillis = Clock.System.now().plus(4.days).toEpochMilliseconds(),
        yearRange = IntRange(2023, 2100),
        initialDisplayMode = DisplayMode.Picker
    )

    DatePickerDialog(
    shape = RoundedCornerShape(6.dp),
    onDismissRequest = {},
    confirmButton = {
        TextButton(onClick = {
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