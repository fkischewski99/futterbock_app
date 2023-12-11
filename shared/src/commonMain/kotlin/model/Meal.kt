package model

import view.shared.ListItem
import java.time.LocalDate

data class Meal(
    val day: LocalDate,
    val mealType: String,
    val recipeSelections: List<RecipeSelection>
)

// Extension function to map Meal to ListItem
fun Meal.getListItems(): List<ListItem> {
    val recipeSelectionItems = recipeSelections.map { recipeSelection ->
        recipeSelection.toListItem()
    }
    return recipeSelectionItems


}

