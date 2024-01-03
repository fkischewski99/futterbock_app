package view.event.recepie_overview_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import model.Recipe
import view.shared.NavigationIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeOverview(recipe: Recipe) {
    val navigator = LocalNavigator.currentOrThrow
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Rezeptübersicht") },
                navigationIcon = {
                    NavigationIconButton()
                }

            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background).padding(top = it.calculateTopPadding())
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState()).statusBarsPadding().navigationBarsPadding()) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    elevation = CardDefaults.cardElevation(16.dp)
                ) {
                    Text(
                        text = "${recipe.name}",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(8.dp)
                    )
                    TextField(
                        value = recipe.description,
                        onValueChange = { /* Handle value change if needed */ },
                        label = { Text(text = "Beschreibung:") }, // Input field label
                        singleLine = false, // Single line input
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    )
                    TextField(
                        value = recipe.price + "€",
                        onValueChange = { /* Handle value change if needed */ },
                        label = { Text(text = "Preis:") }, // Input field label
                        singleLine = true, // Single line input
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    )
                    TextField(
                        value = recipe.season,
                        onValueChange = { /* Handle value change if needed */ },
                        label = { Text(text = "Saison:") }, // Input field label
                        singleLine = true, // Single line input
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Text(
                        text = "Zutaten:",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(8.dp)
                    )
                    recipe.ingredients.forEach { ingredientAmount ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "" + ingredientAmount.amount + ingredientAmount.scoutUnit,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 8.dp).width(100.dp)
                            )
                            Text(
                                text = ingredientAmount.name,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Text(
                        text = "Material:",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(8.dp)
                    )
                    recipe.materials.forEach { material ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = material,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Text(
                        text = "Kochanweisungen:",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    recipe.cookingInstructions.forEachIndexed { index, instruction ->
                        Text(
                            text = "Schritt " + (index + 1) + ":",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp, start = 8.dp)
                        )
                        Text(
                            text = instruction,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 8.dp)

                        )

                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // ... weitere Informationen aus der Recipe-Klasse
                }
            }

        }
    }
}
