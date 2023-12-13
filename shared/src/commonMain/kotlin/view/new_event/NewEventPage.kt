package view.new_event

import AppTheme
import CardWithList
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import model.Meal
import model.RecipeSelection
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import model.toListItem
import kotlin.time.Duration.Companion.days


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEventPage() {
    var name by remember { mutableStateOf(TextFieldValue()) }
    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = Clock.System.now().toEpochMilliseconds(),
        initialSelectedEndDateMillis = Clock.System.now().plus(4.days).toEpochMilliseconds(),
        yearRange = IntRange(2023, 2100),
        initialDisplayMode = DisplayMode.Picker
    )

    var endDate by remember { mutableStateOf(TextFieldValue()) }
    var meal = Meal(
        LocalDate.parse("2023-12-01"),
        "Mittag",
        listOf(RecipeSelection("ab", ArrayList(), "Rezept 1"))
    )
    var meal2 = Meal(
        LocalDate.parse("2023-12-03"),
        "Abend",
        listOf(RecipeSelection("ab", ArrayList(), "Rezept 1"))
    )
    var meals = listOf(meal, meal2);


    val startDate2 = LocalDate.parse("2023-12-01")
    val endDate2 = LocalDate.parse("2023-12-06")
    val daysBetween = endDate2.minus(startDate2).days
    val mealsGroupedByDate = mutableMapOf<LocalDate, MutableList<Meal>>()


    for (i in 0..daysBetween) {
        val currentDate = startDate2.plus(DatePeriod(0, 0, i))
        val mealsForCurrentDate = meals.filter { it.day == currentDate }.toMutableList()
        mealsGroupedByDate[currentDate] = mealsForCurrentDate
    }


    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Neues Lager Erstellen",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Column(
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 0.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {

                    OutlinedTextField(value = name.text,
                        onValueChange = { name = TextFieldValue(it) },
                        label = { Text("Name:") },
                        modifier = Modifier.padding(8.dp)
                    )
                    SimpleDateRangePickerInDatePickerDialog(

                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                            .padding(top = 8.dp)
                    ) {
                        mealsGroupedByDate.forEach { it ->
                            CardWithList("" + it.key.dayOfMonth + "." + it.key.month,
                                it.value.map { meal -> meal.toListItem() })
                        }
                    }


                    // Rest of the code remains the same...
                }
            }

        }
    }
}
