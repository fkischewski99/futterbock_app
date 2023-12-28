package view.new_meal_screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import model.Participant

class NewMealScreen(private val participants: List<Participant>): Screen {
    @Composable
    override fun Content() {
        NewMealPage(participants = participants)
    }
}