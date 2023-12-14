package view.new_event

import AppTheme
import CardWithList
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

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
import kotlinx.datetime.toLocalDateTime
import model.toListItem
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Duration.Companion.days


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEventPage() {
    var name by remember { mutableStateOf(TextFieldValue()) }
    var mealsGroupedByDate by remember {
        mutableStateOf<Map<LocalDate, MutableList<Meal>>>(mutableMapOf())
    }


    //TODO remove test DATA
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
    //End of Test Data

    val navigateToNewListItem: () -> Unit = {
        //Todo add navigation
    }

    val onDateSelectFunction: (selectedStartMilis: Long, selectedEndMilis: Long) -> Unit =
        { startMillis, endMillis ->
            val updatedMap = mutableMapOf<LocalDate, MutableList<Meal>>();
            val startDateSelect = Instant.fromEpochMilliseconds(startMillis)
            val endDateSelect = Instant.fromEpochMilliseconds(endMillis)
            val daysBetween = endDateSelect.minus(startDateSelect).inWholeDays;
            for (i in 0..daysBetween) {
                val currentInstant = startDateSelect.plus(i.days)
                val currentDate =
                    currentInstant.toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).date;
                val mealsForCurrentDate = meals.filter { it.day == currentDate }.toMutableList()
                updatedMap[currentDate] = mealsForCurrentDate
            }
            mealsGroupedByDate = updatedMap.toMap();

        }


    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Neues Lager Erstellen",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp).weight(1f),


                        )
                    IconButton(
                        onClick = { /* Handle shopping cart icon click */ },
                        modifier = Modifier.weight(0.15f).clip(shape = RoundedCornerShape(75))
                            .background(MaterialTheme.colorScheme.tertiary),

                        ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Shopping Cart Icon"
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = { /* Handle printer icon click */ },
                        modifier = Modifier.weight(0.15f).clip(shape = RoundedCornerShape(75))
                            .background(MaterialTheme.colorScheme.tertiary),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Printer Icon"
                        )
                    }
                }

                Row() {
                    OutlinedTextField(
                        value = name.text,
                        onValueChange = { name = TextFieldValue(it) },
                        label = { Text("Name:") },
                        modifier = Modifier.padding(8.dp)
                    )
                }
                SimpleDateRangePickerInDatePickerDialog(
                    onDateSelectFunction
                )
                Spacer(modifier = Modifier.height(16.dp))
                mealsGroupedByDate.forEach { it ->
                    CardWithList(
                        "" + it.key.dayOfMonth + "." + it.key.month,
                        it.value.map { meal -> meal.toListItem() }, navigateToNewListItem
                    )
                }


                // Rest of the code remains the same...
            }
        }

    }
}

fun calculateDaysInBeetween() {

}
