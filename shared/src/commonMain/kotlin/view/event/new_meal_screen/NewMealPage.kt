package view.event.new_meal_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import model.Meal
import model.Participant
import model.Recipe
import view.event.recepie_overview_screen.RecepieScreen
import view.shared.NavigationIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMealPage(meal: Meal, participants: List<Participant>) {
    val navigator = LocalNavigator.currentOrThrow
    //var query by remember { mutableStateOf("") }

    var selectedRecepies =
        mutableStateListOf(*meal.recipeSelections.toTypedArray())



    //val context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
    ) {
        Scaffold(
            topBar = {
                SearchBarComponent(selectedRecepies)
            }
        ) {

            Column(
                modifier = Modifier.padding(top = it.calculateTopPadding()).padding(top = 8.dp).verticalScroll(rememberScrollState())
            ) {
                DropdownMenuMeal(meal)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Rezepte",  style = MaterialTheme.typography.headlineSmall,)
                selectedRecepies.forEach {
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(8.dp),
                        modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Rezept ansehen",
                                modifier = Modifier.clickable {
                                    navigator.push(RecepieScreen())
                                })
                            Text(
                                it.name,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(8.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        // Replace this with your recipe content
                        RecipeWithMembers(participants)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent(selectedRecepies: SnapshotStateList<Recipe>) {
    var searchText by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var recepieList = ExampleObjects.getAllRecepies().toTypedArray()

    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!active) {
            NavigationIconButton()
        }
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = searchText,
            placeholder = { Text(text = "Rezept auswählen") },
            onQueryChange = {
                searchText = it
            },
            onSearch = {
                active = false
            },
            active = active,
            onActiveChange = {
                active = it
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Suche")
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (searchText.isEmpty()) {
                                active = false
                            } else {
                                searchText = ""
                            }

                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }
            }
        ) {
            recepieList.filter {
                it.name.lowercase().contains(searchText.lowercase())
            }.forEach {
                Row(
                    modifier = Modifier.padding(16.dp).clickable {
                        active = false
                        searchText = ""
                        selectedRecepies.add(it)
                    }

                ) {
                    Text(text = it.name)
                }
                Divider()
            }
        }
    }
}

@Composable
fun RecipeWithMembers(participants: List<Participant>) {
    val members = participants.map { it.firstName + " " + it.lastName } // Example members
    val checkedState = remember {
        mutableStateMapOf<String, Boolean>().apply {
            members.forEach { member ->
                this[member] = true // By default, every checkbox is selected
            }
        }
    }

    var expanded by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState.values.all { it },
                onCheckedChange = { isChecked ->
                    members.forEach { member ->
                        checkedState[member] = isChecked
                    }
                }
            )
            Text(
                text = "Alle auswählen",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        if (expanded) {
            members.forEach { member ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checkedState[member] ?: false,
                        onCheckedChange = { isChecked ->
                            checkedState[member] = isChecked
                        }
                    )
                    Text(
                        text = member,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
