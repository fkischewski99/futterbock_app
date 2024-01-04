package model

data class Ingredient(
    val objectId: String = "",
    val category: String,
    val conversionFactor: Double,
    val scoutUnit: String,
    val metricUnit: String,
    val name: String,
    val amount: Int,
    var shoppingDone: Boolean = false,
    var note: String = ""
)