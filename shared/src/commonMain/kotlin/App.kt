import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        var currentList by remember { mutableStateOf(listOf("Item 1", "Item 2")) }
        var pastList by remember { mutableStateOf(listOf("Past Item 1", "Past Item 2")) }
        var isExpanded by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Lagerübersicht 123") },
                )
            }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = 4.dp // Adjust elevation for the card shadow
            ) {
                LazyColumn {
                    item {
                        Text("Aktuell", style = MaterialTheme.typography.h6)
                        IconButton(
                            onClick = {
                                currentList =
                                    currentList + "Neues Lager" // Adding new item to the current list
                            },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add"
                            )
                        }

                    }
                    items(currentList.size) { index ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    // Action on clicking an item in the current list
                                }
                                .padding(vertical = 8.dp, horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = currentList[index],
                                style = MaterialTheme.typography.subtitle1,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    item {
                        Text(
                            text = if (isExpanded) "Listtitle: Vergangene" else "Listtitle: Vergangene (Expandable)",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.clickable { isExpanded = !isExpanded }
                        )
                    }
                    if (isExpanded) {
                        items(pastList.size) { index ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        // Action on clicking an item in the past list
                                    }
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = pastList[index],
                                    style = MaterialTheme.typography.subtitle1
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

expect fun getPlatformName(): String