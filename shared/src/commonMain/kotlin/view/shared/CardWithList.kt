import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import view.shared.ListItem

@Composable
fun <T> CardWithList(
    title: String,
    listItems: List<ListItem<T>>,
    addItemToList: (() -> Unit)? = null,
    onDeleteClick: ((ListItem<T>) -> Unit)? = null,
    onListItemClick: ((ListItem<T>) -> Unit)? = null
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }

        listItems.forEach { listItem ->
            ListItemComponent(listItem = listItem, onDeleteClick = onDeleteClick, onListItemClick = onListItemClick)
        }
        if (listItems.isEmpty()) {
            Spacer(Modifier.height(16.dp))
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            addItemToList?.let {
                FloatingActionButton(
                    onClick = { addItemToList.invoke() },
                    modifier = Modifier.clip(shape = RoundedCornerShape(50)),
                    containerColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add, contentDescription = "Add Icon"
                    )
                }
            }
        }
    }
}

@Composable
fun <T> ListItemComponent(
    listItem: ListItem<T>,
    onDeleteClick: ((ListItem<T>) -> Unit)? = null,
    onListItemClick: ((ListItem<T>) -> Unit)? = null
) {
    val navigator = LocalNavigator.currentOrThrow
    var showDialog by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxWidth().clickable {
        if (onListItemClick != null) {
            onListItemClick(listItem)
        } else {
            navigator.push(listItem.navigateTo())
        }
    }.padding(16.dp)) {
        Column {
            Text(text = listItem.getTitle(), color = Color.Black)
            Text(text = listItem.getSubtitle(), color = Color.Gray)
        }
        if (
            onDeleteClick != null
        ) {
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { showDialog = true },
                modifier = Modifier.padding(end = 8.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Item",
                    tint = Color.Red
                )
            }
            if(showDialog){
                ConfirmDialog({ onDeleteClick(listItem) }, {showDialog = false})
            }
        }
    }
}

@Composable
fun ConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Löschen Bestätigen") },
            text = { Text(text = "Willst du das Item wirklich löschen?") },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm()
                        onDismiss()
                    }
                ) {
                    Text("Bestätigen")
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss
                ) {
                    Text("Abbrechen")
                }
            }
        )
}

