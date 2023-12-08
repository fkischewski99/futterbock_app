package model

data class Meal(
    val day: String,
    val meal_type: String,
    val recipe_selections: List<RecipeSelection>
)