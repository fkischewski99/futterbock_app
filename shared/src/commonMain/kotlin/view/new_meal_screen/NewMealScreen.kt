package view.new_meal_screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import view.new_meal_screenimport.NewMealPage

class NewMealScreen: Screen {
    @Composable
    override fun Content() {
        NewMealPage {  }
    }
}