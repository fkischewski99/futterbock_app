import androidx.compose.ui.window.ComposeUIViewController
import modules.dataModules
import modules.viewModelModules
import org.koin.core.context.startKoin

actual fun getPlatformName(): String = "iOS"

fun MainViewController() = ComposeUIViewController { App() }

fun initKoin(){
    startKoin {
        modules(viewModelModules, dataModules)
    }
}