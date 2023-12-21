package view.recepie_overview_screen

import androidx.compose.runtime.Composable
import model.IngredientAmount
import model.Recipe

@Composable
fun RecepieScreen() {
    val recipe = Recipe(
        cookingInstructions = listOf("Instruction 1", "Instruction 2"),
        description = "Apfelkuchen",
        dietaryHabits = "Vegan",
        ingredients = listOf(
            //TODO das ist noch richtig falsch :D
            IngredientAmount(amount = "200g", ingredientId = "Apfel"),
            IngredientAmount(amount = "100g", ingredientId = "Zucker")
        ),
        materials = listOf("Backform", "Schneebesen"),
        pageInCookbook = 10,
        price = "5.99",
        season = "Herbst",
        source = "Omas Kochbuch",
        time = "1 Stunde",
        name = "Rezeptname"
    )

    RecipeOverview(recipe = recipe)
}