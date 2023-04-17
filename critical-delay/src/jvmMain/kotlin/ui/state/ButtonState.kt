package ui.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberButtonState(
    enableWhen: () -> Boolean = { true },
    onHover: String = "",
    onClick: () -> Unit = {}
)  = remember(enableWhen, onHover, onClick) {
    ButtonState(enableWhen, onHover, onClick)
}

data class ButtonState(
    val enableWhen: () -> Boolean,
    val onHover: String = "",
    val onClick: () -> Unit
)