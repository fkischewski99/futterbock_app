package view.event.new_event

import AppTheme
import CardWithList
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Instant
import model.Meal
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import model.Event
import model.MealType
import model.toListItem
import ui.ListToListWithReorderScreen
import view.event.new_meal_screen.NewMealScreen
import view.event.participants.ParticipantScreen
import view.shared.NavigationIconButton


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun NewEventPage(event: Event) {
    val navigator = LocalNavigator.currentOrThrow
    var name by remember { mutableStateOf(TextFieldValue(event.name)) }
    var mealsGroupedByDate by remember {
        mutableStateOf<Map<LocalDate, MutableList<Meal>>>(mutableMapOf())
    }


    //TODO remove test DATA


    //End of Test Data
    fun groupMealsByDate() {
        if (event.from == null || event.to == null) {
            return;
        }
        val updatedMap = mutableMapOf<LocalDate, MutableList<Meal>>();
        val daysBetween = (event.to!! - event.from!!).days
        for (i in 0..daysBetween) {
            val currentDate = event.from!!.plus(DatePeriod(years = 0, months = 0, days = i))
            val mealsForCurrentDate = event.meals.filter { it.day == currentDate }.toMutableList()
            updatedMap[currentDate] = mealsForCurrentDate
        }
        mealsGroupedByDate = updatedMap.toMap();
    }

    LaunchedEffect(Unit) {
        if (event.to != null && event.from != null) {
            groupMealsByDate();
        }
    }

    fun navigateToNewListItem(date: LocalDate) {
        navigator.push(
            NewMealScreen(
                Meal(
                    day = date,
                    recipeSelections = ArrayList(),
                    mealType = MealType.MITTAG
                ), event.participantsSchedule
            )
        )
    }

    fun navigateToMeal(meal: Meal) {
        navigator.push(NewMealScreen(meal, event.participantsSchedule))
    }

    val onDateSelectFunction: (selectedStartMilis: Long, selectedEndMilis: Long) -> Unit =
        { startMillis, endMillis ->
            val startDateSelect = Instant.fromEpochMilliseconds(startMillis)
            val endDateSelect = Instant.fromEpochMilliseconds(endMillis)
            event.from =
                startDateSelect.toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).date;
            event.to =
                endDateSelect.toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).date;
            groupMealsByDate();
        }


    AppTheme {
        Scaffold(
            topBar = {
                topBarEventPage()
                }) {
            Surface(
                color = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxSize().padding(top = it.calculateTopPadding())
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = name.text,
                            onValueChange = { name = TextFieldValue(it); event.name = it},
                            label = { Text("Name:") },
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = "" + event.participantsSchedule.size,
                            readOnly = true,
                            onValueChange = { },
                            label = { Text("Teilnehmeranzahl:") },
                            modifier = Modifier.padding(8.dp)
                        )
                        Button(
                            // Calendar icon to open DatePicker
                            onClick = {
                                navigator.push(ParticipantScreen(event))
                            },
                            modifier = Modifier
                                .padding(8.dp).height(IntrinsicSize.Min)
                                .align(Alignment.CenterVertically),

                            ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Person",
                            )
                        }
                    }
                    SimpleDateRangePickerInDatePickerDialog(
                        onDateSelectFunction
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    mealsGroupedByDate.forEach { it ->
                        CardWithList(
                            title =
                            "" + it.key.dayOfMonth + "." + it.key.month,
                            listItems =
                            it.value.map { meal -> meal.toListItem() }.toMutableList(),
                            addItemToList = { navigateToNewListItem(it.key) },
                            onListItemClick = { navigateToMeal(it.getItem()) }
                        )
                    }


                    // Rest of the code remains the same...
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topBarEventPage() {
    val navigator = LocalNavigator.currentOrThrow
    TopAppBar(
        title = {
            Text(text = "Lager bearbeiten") },
        navigationIcon = {
            NavigationIconButton()
        },
        actions = {
            Row(
                horizontalArrangement = Arrangement.End,
            ) {

                IconButton(
                    onClick = { navigator.push(ListToListWithReorderScreen()) },
                    modifier = Modifier.clip(shape = RoundedCornerShape(75))
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
                    modifier = Modifier.clip(shape = RoundedCornerShape(75))
                        .background(MaterialTheme.colorScheme.tertiary),
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Printer Icon"
                    )
                }
            }
        }
    )

}



