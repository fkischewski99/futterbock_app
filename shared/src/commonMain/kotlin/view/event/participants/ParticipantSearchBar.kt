package view.event.participants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import model.Event
import model.Participant
import view.admin.new_participant.NewParicipantScreen
import view.shared.NavigationIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticipantSearchBar(allParicipants: List<Participant>, event: Event) {
    val navigator = LocalNavigator.currentOrThrow
    var searchText by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(true) }
    var allParticipantsState = remember {
        allParicipants
    }
    var selectedParticipants = mutableStateListOf(*event.participantsSchedule.toTypedArray())


    Scaffold(
        topBar = {
            NavigationIconButton()
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                query = searchText,
                placeholder = { Text(text = "Teilnehmer hinzufügen") },
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
                                    navigator.pop()
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
                Row {
                    selectedParticipants.forEach {
                        InputChip(
                            label = {
                                Box(contentAlignment = Alignment.CenterStart) {
                                    Text(text = it.firstName + " " + it.lastName)
                                }
                            },
                            onClick = { selectedParticipants.remove(it) },
                            selected = true,
                            modifier = Modifier.padding(4.dp),
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    modifier = Modifier
                                        .padding(start = 4.dp)
                                        .clickable { selectedParticipants.remove(it) }
                                )
                            }

                        )
                    }
                }
                allParticipantsState.filter {
                    it.firstName.lowercase().contains(searchText.lowercase()) ||
                            it.lastName.lowercase().contains(searchText.lowercase()) ||
                            (it.firstName.lowercase() + " " + it.lastName.lowercase()).contains(
                                searchText.lowercase()
                            )
                }.forEach {
                    Row(
                        modifier = Modifier.padding(16.dp).clickable {
                            searchText = ""
                            it.from = event.from
                            it.to = event.to
                            selectedParticipants.add(it);
                        }

                    ) {
                        Text(text = it.firstName + " " + it.lastName)
                    }
                    Divider()
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Column(
                        horizontalAlignment = Alignment.End,
                    ) {
                        ExtendedFloatingActionButton(
                            onClick = {
                                //TODO
                                navigator.push(NewParicipantScreen(null));
                                event.participantsSchedule = selectedParticipants
                            },
                            modifier = Modifier.padding(bottom = 16.dp)
                                .clip(shape = RoundedCornerShape(75)), // Limit the width to prevent stretching,
                            containerColor = MaterialTheme.colorScheme.onPrimary,

                            ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Icon"
                            )
                            Text("Teilnehmer anlegen       ")
                        }
                        ExtendedFloatingActionButton(
                            onClick = {
                                navigator.pop()
                                event.participantsSchedule = selectedParticipants
                            },
                            modifier = Modifier.padding(bottom = 16.dp)
                                .clip(shape = RoundedCornerShape(75)), // Limit the width to prevent stretching,
                            elevation = FloatingActionButtonDefaults.elevation(16.dp),
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Add Icon"
                            )
                            Text("Teilnehmer übernehmen")
                        }
                    }
                }
            }

            //elevation = AppBarDefaults.TopAppBarElevation

        }
    ) {

    }
}