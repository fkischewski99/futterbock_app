package view.participants

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import model.Event

class ParticipantScreen(private val event: Event): Screen {
    @Composable
    override fun Content() {
        ParticipantPage(event)
    }
}