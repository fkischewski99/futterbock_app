package view.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

@Composable
fun NavigationIconButton(onLeave: (() -> Unit)? = null) {
    val navigator = LocalNavigator.currentOrThrow
    IconButton(
        onClick = {
            if(onLeave != null){
                onLeave()
            }
            navigator.pop()
        }
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Zurück"
        )
    }
}