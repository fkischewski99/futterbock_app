package model

import view.shared.ListItem

data class RecipeSelection(
    val feedback: String,
    val participants: List<String>,
    val recipe: String
)
