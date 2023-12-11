package view.new_event

import AppTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.Meal
import model.RecipeSelection
import java.time.LocalDate
import java.time.temporal.ChronoUnit


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewEventPage() {
    var name by remember { mutableStateOf(TextFieldValue()) }
    var startDate by remember { mutableStateOf(TextFieldValue()) }
    var endDate by remember { mutableStateOf(TextFieldValue()) }
    var meal = Meal(LocalDate.of(2023, 12, 1), "Mittag", listOf(RecipeSelection("ab", ArrayList(), "Rezept 1")))
    var meal2 = Meal(LocalDate.of(2023, 12, 2), "Abend", listOf(RecipeSelection("ab", ArrayList(), "Rezept 1")))
    var meals = listOf(meal, meal2);


    val startDate2 = LocalDate.of(2023, 12, 1)
    val endDate2 = LocalDate.of(2023, 12, 10)
    val daysBetween = ChronoUnit.DAYS.between(startDate2, endDate2)
    val mealsGroupedByDate = mutableMapOf<LocalDate, MutableList<Meal>>()

    for (i in 0..daysBetween) {

        // Replace this logic with your way of generating meals for a specific day
        val generatedMeals = meals.filter { meal -> meal.day == startDate2 }
        if(generatedMeals != null){
            //mealsGroupedByDate[startDate2].addAll(generatedMeals)
        }

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
                OutlinedTextField(
                    value = name.text,
                    onValueChange = { name = TextFieldValue(it) },
                    label = { Text("Name:") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column {
                    OutlinedTextField(
                        value = startDate.text,
                        onValueChange = { startDate = TextFieldValue(it) },
                        label = { Text("Start:") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = endDate.text,
                    onValueChange = { endDate = TextFieldValue(it) },
                    label = { Text("Ende:") },
                    modifier = Modifier.fillMaxWidth()
                )
                Column {

                }


                // Rest of the code remains the same...
            }
        }
    }
}
