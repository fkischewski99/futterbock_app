package view.new_meal_screenimport

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMealPage(onSearch: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    //var query by remember { mutableStateOf("") }
    var recepieList = remember {
        mutableStateListOf(
            "Brot",
            "Kuchen",
            "Aldi",
            "Lecker Essen",
        )
    }
    var selectedRecepies = remember {
        mutableStateListOf("Reis mit Scheiß")
    }

    //val context = LocalContext.current

    Scaffold(
        topBar = {
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
                    it.lowercase().contains(searchText.lowercase())
                }.forEach {
                    Row(
                        modifier = Modifier.padding(16.dp).clickable {
                            active = false
                            searchText = ""
                            selectedRecepies.add(it)
                        }

                    ) {
                        Text(text = it)
                    }
                }
            }
            //elevation = AppBarDefaults.TopAppBarElevation

        }
    ) {

        Column(
            modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            selectedRecepies.forEach {
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(8.dp),
                    modifier = Modifier.padding(top = 16.dp, start = 8.dp)
                ) {
                    Text(it, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(8.dp), textAlign = TextAlign.Center)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Replace this with your recipe content
                    RecipeWithMembers()
                }
            }
        }
    }
}

@Composable
fun RecipeWithMembers() {
    val members = listOf("Frederik K", "Ronja W", "Young Flex", "Old Flex") // Example members
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
