package model

data class Recipe(
    val name: String,
    val cookingInstructions: List<String>,
    val description: String,
    val dietaryHabits: String,
    val ingredients: List<Ingredient>,
    val materials: List<String>,
    val pageInCookbook: Int,
    val price: String,
    val season: String,
    val source: String,
    val time: String,
    val eaters: List<Participant>
)