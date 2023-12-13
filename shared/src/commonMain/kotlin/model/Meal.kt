package model

import view.shared.ListItem
import kotlinx.datetime.LocalDate

data class Meal(
    val day: LocalDate,
    val mealType: String,
    val recipeSelections: List<RecipeSelection>
)

fun Meal.toListItem(): ListItem {
    return object : ListItem {
        override fun getTitle(): String {
            return mealType // Custom title using 'meal_type' and 'day'
        }

        override fun getSubtitle(): String {
            // Custom subtitle logic based on 'recipe_selections' or any other properties
            return recipeSelections.joinToString(", ") { it.recipe }
        }

        override fun onClick(): String {
            return "Clicked meal on ListItem" // Example: Generating onClick behavior
        }
    }
}


// Extension function to map Meal to ListItem

