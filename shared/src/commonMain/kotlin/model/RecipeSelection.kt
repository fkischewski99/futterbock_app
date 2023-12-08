package model

data class RecipeSelection(
    val feedback: String,
    val participants: List<String>,
    val recipe: String
)