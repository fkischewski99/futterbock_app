import androidx.compose.runtime.mutableStateListOf
import model.Ingredient

class ShoppingListViewModel{
    val shoppingList = mutableStateListOf(
        Ingredient(
            objectId = "4",
            amount = 500,
            conversionFactor = 1.2,
            name = "Wein",
            category = "bla",
            metricUnit = "g",
            scoutUnit = "g",
            shoppingDone = true
        )
    )

    val completedList = mutableStateListOf<Ingredient>()

    fun toggleShoppingDone(ingredient: Ingredient) {
        if (ingredient.shoppingDone) {
            shoppingList.add(ingredient)
            completedList.remove(ingredient)
        } else {
            shoppingList.remove(ingredient)
            completedList.add(ingredient)
        }
        ingredient.shoppingDone = !ingredient.shoppingDone
    }
}
