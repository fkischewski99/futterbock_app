package view.event.homescreen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import data.Querys
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

class HomeScreen: Screen{
    @Composable
    override fun Content() {
        val viewModel = getViewModel(Unit, viewModelFactory { ViewModelEventOverview(Querys()) })
        EventOverview(viewModel)
    }
}