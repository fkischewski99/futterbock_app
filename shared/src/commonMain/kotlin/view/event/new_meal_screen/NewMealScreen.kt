package view.event.new_meal_screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import model.Meal
import model.Participant

class NewMealScreen(private val meal: Meal, private val participants: List<Participant>): Screen {
    @Composable
    override fun Content() {
        NewMealPage(meal = meal, participants = participants)
    }
}