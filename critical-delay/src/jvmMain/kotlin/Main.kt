import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import viewmodel.MainScreenViewModel

@Composable
@Preview
fun App() {
    val applicationScope = rememberCoroutineScope()
    val viewModel = MainScreenViewModel(applicationScope)

    MaterialTheme {
        MainScreen(viewModel)
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
