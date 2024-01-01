package view.event.recepie_overview_screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import model.Ingredient
import model.Recipe


class RecepieScreen: Screen {
    val recipe = Recipe(
        cookingInstructions = listOf("Instruction 1", "Instruction 2"),
        description = "Apfelkuchen",
        dietaryHabits = "Vegan",
        ingredients = listOf(
            //TODO das ist noch richtig falsch :D
            Ingredient(amount = 200, conversionFactor = 1.2 , name = "Apfel", category = "bla", metricUnit = "g", scoutUnit = "g"),
            Ingredient(amount = 500, conversionFactor = 1.2 , name = "Zucker", category = "bla", metricUnit = "g", scoutUnit = "g"),
        ),
        materials = listOf("Backform", "Schneebesen"),
        pageInCookbook = 10,
        price = "5.99",
        season = "Herbst",
        source = "Omas Kochbuch",
        time = "1 Stunde",
        name = "Rezeptname",
        eaters = ArrayList()
    )

    @Composable
    override fun Content() {
        RecipeOverview(recipe = recipe)
    }

}