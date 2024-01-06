package view.event.shopping_list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.Ingredient

@Composable
fun ShoppingList(ingredientsList: List<Ingredient>) {
    var expanded by remember { mutableStateOf(true) }
    var uncheckedIngredients by remember { mutableStateOf(ingredientsList.filter { !it.shoppingDone }) }
    var checkedIngredients by remember { mutableStateOf(ingredientsList.filter { it.shoppingDone }) }

    fun onCheckboxClicked(ingredient: Ingredient) {
        ingredient.shoppingDone = !ingredient.shoppingDone
        if (ingredient.shoppingDone) {
            uncheckedIngredients = uncheckedIngredients.filter { it != ingredient }
            checkedIngredients = checkedIngredients + ingredient
        } else {
            checkedIngredients = checkedIngredients.filter { it != ingredient }
            uncheckedIngredients = uncheckedIngredients + ingredient
        }
    }

    Spacer(modifier = Modifier.padding(top = 8.dp))
    uncheckedIngredients.forEach { ingredient ->
        ShoppingItem(ingredient = ingredient, onCheckboxClicked = {ingredient -> onCheckboxClicked(ingredient)})
    }
Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(top = 16.dp, start = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (expanded) "Erledigte" else "Erledigte einblenden",
            style = MaterialTheme.typography.headlineSmall,
        )
        Icon(
            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }

    if (expanded) {
        checkedIngredients.forEach { ingredient ->
            ShoppingItem(ingredient, onCheckboxClicked = {ingredient -> onCheckboxClicked(ingredient)})
        }
    }
}

@Composable
fun ShoppingItem(
    ingredient: Ingredient,
    onCheckboxClicked: (Ingredient) -> Unit = {},
) {
    var noteState by remember { mutableStateOf(ingredient.note) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(4.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
            .height(48.dp)
    ) {
        Checkbox(
            ingredient.shoppingDone,
            onCheckedChange = { onCheckboxClicked(ingredient) },
            colors = CheckboxDefaults.colors(uncheckedColor = MaterialTheme.colorScheme.onPrimary)
        )
        Text(
            text = "" + ingredient.amount + ingredient.metricUnit,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 8.dp).width(70.dp),
        )
        Text(
            text = ingredient.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.width(70.dp),
        )
        BasicTextField(
            value = noteState,
            onValueChange = { noteState = it },
            singleLine = true,
            //label = { Text("Notiz:") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}