import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import view.shared.ListItem

@Composable
fun CardWithList(title: String, listItems: List<ListItem>) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column {
                listItems.forEach { listItem ->
                    ListItemComponent(listItem = listItem)
                }
            }
        }
    }
}

@Composable
fun ListItemComponent(listItem: ListItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { listItem.onClick() }
            .padding(16.dp)
    ) {
        Text(text = listItem.getTitle(), color = Color.Black)
        Text(text = listItem.getSubtitle(), color = Color.Gray)
    }
}
