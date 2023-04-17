package ui.state

import androidx.compose.runtime.*
import model.Progress
import model.zero

@Composable
fun rememberProgressIndicatorState(show: Boolean = false) = remember { ProgressIndicatorState(show) }

class ProgressIndicatorState(show: Boolean) {
    var show by mutableStateOf(show)
    var progress: Progress by mutableStateOf(zero())
}
