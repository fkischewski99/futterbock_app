package model

data class Ingredient(
    val category: String,
    val conversionFactor: Double,
    val scoutUnit: String,
    val metricUnit: String,
    val name: String,
    val amount: Int,
)