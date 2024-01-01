package view.event.new_meal_screen

import model.Ingredient
import model.Recipe

class ExampleObjects {


    companion object {
        fun getAllRecepies(): List<Recipe>{
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
            name = "Reis mit allem",
            eaters = ArrayList()
            )

            val recipe2 = Recipe(
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
                name = "Apfelkuchen",
                eaters = ArrayList()
            )
            return listOf(recipe2, recipe)
        }
    }
}