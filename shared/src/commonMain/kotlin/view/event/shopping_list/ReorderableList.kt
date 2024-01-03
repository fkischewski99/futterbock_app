package view.event.shopping_list

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mohamedrejeb.compose.dnd.annotation.ExperimentalDndApi
import com.mohamedrejeb.compose.dnd.reorder.ReorderContainer
import com.mohamedrejeb.compose.dnd.reorder.ReorderableItem
import com.mohamedrejeb.compose.dnd.reorder.rememberReorderState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import model.Ingredient
import view.shared.NavigationIconButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReorderableList(ingredients: List<Ingredient>) {
    val navigator = LocalNavigator.currentOrThrow

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
    ) { paddingValues ->
        ListToListWithReorderContent(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(paddingValues)
                .padding(20.dp),
            ingredientsList = ingredients
        )

    }
}

@OptIn(ExperimentalDndApi::class)
@Composable
private fun ListToListWithReorderContent(
    modifier: Modifier,
    ingredientsList: List<Ingredient>
) {
    var items by remember {
        mutableStateOf(
            ingredientsList
        )
    }

    val scope = rememberCoroutineScope()
    val reorderState = rememberReorderState<Ingredient>()
    val lazyListState = rememberLazyListState()

    ReorderContainer(
        state = reorderState,
        modifier = modifier,
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(items, key = { it }) { item ->
                ReorderableItem(
                    state = reorderState,
                    key = item,
                    data = item,
                    onDrop = {},
                    onDragEnter = { state ->
                        items = items.toMutableList().apply {
                            val index = indexOf(item)
                            if (index == -1) return@ReorderableItem
                            remove(state.data)
                            add(index, state.data)

                            scope.launch {
                                handleLazyListScroll(
                                    lazyListState = lazyListState,
                                    dropIndex = index,
                                )
                            }
                        }
                    },
                    draggableContent = {
                        RedBox(
                            isDragShadow = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            ingredient = item
                        )
                    },
                    modifier = Modifier
                ) {
                    RedBox(
                        modifier = Modifier
                            .graphicsLayer {
                                alpha = if (isDragging) 0f else 1f
                            }
                            .fillMaxWidth()
                            .height(60.dp),
                        ingredient = item
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedBox(
    isDragShadow: Boolean = false,
    modifier: Modifier = Modifier,
    ingredient: Ingredient
) {

    var note by remember { mutableStateOf(ingredient.note) }

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .then(
                if (isDragShadow) {
                    Modifier
                        .shadow(
                            elevation = 20.dp,
                            shape = RoundedCornerShape(24.dp),
                        )
                } else {
                    Modifier
                }
            )
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                false,
                onCheckedChange = {},
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
            Text(
                text = ingredient.note,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.width(70.dp),
            )
        }
    }
}

suspend fun handleLazyListScroll(
    lazyListState: LazyListState,
    dropIndex: Int,
): Unit = coroutineScope {
    val firstVisibleItemIndex = lazyListState.firstVisibleItemIndex
    val firstVisibleItemScrollOffset = lazyListState.firstVisibleItemScrollOffset

    // Workaround to fix scroll issue when dragging the first item
    if (dropIndex == 0 || dropIndex == 1) {
        launch {
            lazyListState.scrollToItem(firstVisibleItemIndex, firstVisibleItemScrollOffset)
        }
    }

    // Animate scroll when entering the first or last item
    val lastVisibleItemIndex =
        lazyListState.firstVisibleItemIndex + lazyListState.layoutInfo.visibleItemsInfo.lastIndex

    val firstVisibleItem =
        lazyListState.layoutInfo.visibleItemsInfo.firstOrNull() ?: return@coroutineScope
    val scrollAmount = firstVisibleItem.size * 2f

    if (dropIndex <= firstVisibleItemIndex + 1) {
        launch {
            lazyListState.animateScrollBy(-scrollAmount)
        }
    } else if (dropIndex == lastVisibleItemIndex) {
        launch {
            lazyListState.animateScrollBy(scrollAmount)
        }
    }
}