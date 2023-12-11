package model

import view.shared.ListItem

data class RecipeSelection(
    val feedback: String,
    val participants: List<String>,
    val recipe: String
)

fun RecipeSelection.toListItem(): ListItem {
    return object : ListItem {
        override fun getTitle(): String {
            return recipe // Custom title using 'meal_type' and 'day'
        }

        override fun getSubtitle(): String {
            // Custom subtitle logic based on 'recipe_selections' or any other properties
            return participants.joinToString(", ") { it }
        }

        override fun onClick(): String {
            return "Clicked meal on ${this@toListItem.feedback}" // Example: Generating onClick behavior
        }
    }
}