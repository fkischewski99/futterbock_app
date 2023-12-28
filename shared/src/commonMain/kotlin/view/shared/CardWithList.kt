import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun CardWithList(title: String, listItems: List<ListItem>, addItemToList: (() -> Unit)? = null) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                )
            }

            listItems.forEach { listItem ->
                ListItemComponent(listItem = listItem)
            }
            if(listItems.isEmpty()){
                Spacer(Modifier.height(16.dp))
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
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
}

@Composable
fun ListItemComponent(listItem: ListItem) {
    val navigator = LocalNavigator.currentOrThrow

    Column(modifier = Modifier.fillMaxWidth().clickable {
        navigator.push(listItem.navigateTo())
    }.padding(16.dp)) {
        Text(text = listItem.getTitle(), color = Color.Black)
        Text(text = listItem.getSubtitle(), color = Color.Gray)
    }
}
