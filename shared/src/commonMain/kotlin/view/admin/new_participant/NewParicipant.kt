package view.admin.new_participant

import AppTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import model.EatingHabit
import model.Participant
import view.shared.DatePickerDialog
import view.shared.DateRangePickerDialog
import view.shared.HelperFunctions

@Composable
fun NewParicipant(participant: Participant, onSave: ((participant: Participant) -> Unit)) {

    val navigator = LocalNavigator.currentOrThrow
    var firstName by remember { mutableStateOf(TextFieldValue(participant.firstName)) }
    var lastName by remember { mutableStateOf(TextFieldValue(participant.lastName)) }
    // State to keep track of the selected eating habit

    // State to track whether the dropdown menu is expanded
    var showDatePicker by remember { mutableStateOf(false) }

    AppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Teilnehmer bearbeiten",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth(),


                        )
                }
                Row {
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = {
                                        participant.firstName = it.text; firstName = it},
                        label = { Text("Vorname:") },
                        modifier = Modifier.padding(8.dp).fillMaxWidth()
                    )
                }

                Row {
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { participant.lastName = it.text; lastName = it },
                        label = { Text("Nachname:") },
                        modifier = Modifier.padding(8.dp).fillMaxWidth()
                    )
                }
                Row {
                    dropDownMenu(participant)
                }
                Row {
                    OutlinedTextField(
                        value = "" + participant.birthdate?.let {
                            HelperFunctions.formatDate(it)
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {showDatePicker = true}
                            ){
                                Icon(imageVector = Icons.Default.Edit, contentDescription = "Bearbeiten", modifier = Modifier.size(32.dp))
                            }},
                        label = { Text("Geburtsdatum:") },
                        onValueChange = { },
                        readOnly = true,
                        enabled = true,
                        modifier = Modifier
                            .padding(8.dp).height(IntrinsicSize.Min).fillMaxWidth()
                        )

                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth()
                ) {
                    Button(
                        // Calendar icon to open DatePicker
                        onClick = {
                            onSave(participant)
                            navigator.pop()
                        },
                        modifier = Modifier
                            .padding(16.dp).size(width = 200.dp, height = 60.dp),


                        ) {
                        Text("Speichern")

                    }
                }
                if (showDatePicker) {
                    DatePickerDialog(
                        onSelect = { millis ->
                            participant.birthdate =
                                Instant.fromEpochMilliseconds(millis).toLocalDateTime(
                                    TimeZone.currentSystemDefault()
                                ).date;
                            showDatePicker = false // Set value to false after onSelect is executed
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropDownMenu(participant: Participant){
    var expanded by remember { mutableStateOf(false) }
    var selectedHabit by remember { mutableStateOf<EatingHabit?>(participant.eatingHabit) }

    Box(
        modifier = Modifier
            .fillMaxWidth().padding(8.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                label = { Text("Ernährungsweise:") },
                value = "" + selectedHabit?.name,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                EatingHabit.values().forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.name) },
                        onClick = {
                            selectedHabit = item
                            participant.eatingHabit = item
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}