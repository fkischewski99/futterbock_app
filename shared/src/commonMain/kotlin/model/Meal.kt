package model

import cafe.adriel.voyager.core.screen.Screen
import view.shared.ListItem
import kotlinx.datetime.LocalDate

data class Meal(
    val day: LocalDate,
    val mealType: MealType,
    val recipeSelections: List<Recipe>
)

enum class MealType{
    FRÜHSTÜCK,
    MITTAG,
    ABENDESSEN,
    SNACK
}

fun Meal.toListItem(): ListItem<Meal> {
    return object : ListItem<Meal> {
        override fun getTitle(): String {
            return mealType.name // Custom title using 'meal_type' and 'day'
        }

        override fun getSubtitle(): String {
            // Custom subtitle logic based on 'recipe_selections' or any other properties
            return recipeSelections.joinToString(", ") { it.name }
        }

        override fun navigateTo(): Screen {
            TODO("Not yet implemented")
        }

        override fun getItem(): Meal {
            return this@toListItem
        }
    }
}


// Extension function to map Meal to ListItem

