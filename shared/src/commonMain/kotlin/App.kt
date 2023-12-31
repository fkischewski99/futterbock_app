import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import view.homescreen.HomeScreen

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun App() {
    MaterialTheme {
        var currentList by remember { mutableStateOf(listOf("Item 1", "Item 2")) }
        var pastList by remember { mutableStateOf(listOf("Past Item 1", "Past Item 2")) }
        var isExpanded by remember { mutableStateOf(false) }


        AppTheme{
            //RecepieScreen()
            //NewEventPage()
            Navigator(HomeScreen()) {navigator -> SlideTransition(navigator) }
            //Navigator(AddParticipantToEventScreen()) {navigator -> SlideTransition(navigator) }
        }

    }
}

expect fun getPlatformName(): String