package view.homescreen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class HomeScreen: Screen{
    @Composable
    override fun Content() {
        EventOverview()
    }
}