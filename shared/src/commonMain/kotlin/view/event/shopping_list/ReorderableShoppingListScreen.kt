/*
 * Copyright 2023, Mohamed Ben Rejeb and the Compose Dnd project contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import model.Ingredient
import view.event.shopping_list.ListToListWithReorderContent
import view.event.shopping_list.ShoppedItems
import view.shared.NavigationIconButton

class ListToListWithReorderScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val first = Ingredient(
            objectId = "1",
            amount = 200,
            conversionFactor = 1.2,
            name = "Apfel",
            category = "bla",
            metricUnit = "g",
            scoutUnit = "g",
            note = "abcdefg"
        );
        val second = Ingredient(
            objectId = "2",
            amount = 500,
            conversionFactor = 1.2,
            name = "Zucker",
            category = "bla",
            metricUnit = "g",
            scoutUnit = "g"
        );
        val third = Ingredient(
            objectId = "3",
            amount = 500,
            conversionFactor = 1.2,
            name = "Mehl",
            category = "bla",
            metricUnit = "g",
            scoutUnit = "g"
        );
        val fourth = Ingredient(
            objectId = "4",
            amount = 500,
            conversionFactor = 1.2,
            name = "Wein",
            category = "bla",
            metricUnit = "g",
            scoutUnit = "g",
            shoppingDone = true
        );

        var isView1Visible by remember { mutableStateOf(true) }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Einkaufsliste",
                        )
                    },
                    navigationIcon = {
                        NavigationIconButton()
                    },
                )
            },
        ) {paddingValues ->


            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(paddingValues).padding(start = 32.dp, end = 32.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { isView1Visible = true },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .padding(end = 4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = if (isView1Visible) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(28.dp, 0.dp, 0.dp, 28.dp)
                    ) {
                        Text(
                            text = "Einkaufsliste",
                            color = if (isView1Visible) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Button(
                        onClick = { isView1Visible = false },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = if (!isView1Visible) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(0.dp, 28.dp, 28.dp, 0.dp)
                    ) {
                        Text(
                            text = "Neu Ordnen",
                            color = if (!isView1Visible) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                // Displaying views based on the boolean state
                if (!isView1Visible) {
                    ListToListWithReorderContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .safeDrawingPadding()
                            .padding(20.dp),
                        ingredientsList = listOf(first, second, third, fourth)
                    )
                } else {
                    ShoppedItems(listOf(first, second, third, fourth))
                }
            }
        }
    }
}
