package model

data class Ingredient(
    val objectId: String = "",
    val category: String,
    val conversionFactor: Double,
    val scoutUnit: String,
    val metricUnit: String,
    val name: String,
    val amount: Int,
    var note: String = ""
)