package view.shared

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
fun DatePickerDialog(
 onSelect: ((selectedMilis: Long) -> Unit)
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds(),
        yearRange = IntRange(1950, 2025),
        initialDisplayMode = DisplayMode.Picker
    )

    DatePickerDialog(
    shape = RoundedCornerShape(6.dp),
    onDismissRequest = {},
    confirmButton = {
        TextButton(onClick = {
            //Call function only if correct dates are selected
            if (datePickerState.selectedDateMillis != null) {
                onSelect(
                    datePickerState.selectedDateMillis!!,
                )
            }

            //selectedDate = datePickerState.selectedDateMillis!!
        }) {
            Text(text = "Bestätigen")
        }
    },
    ) {

        DatePicker(
            modifier = Modifier.weight(1f), // Important to display the button
            state = datePickerState,
        )

    }
}