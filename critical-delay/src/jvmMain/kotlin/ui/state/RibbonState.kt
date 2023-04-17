package ui.state

import androidx.compose.runtime.Composable


@Composable
fun rememberRibbonState(
    addButtonState: ButtonState = rememberButtonState(),
    removeButtonState: ButtonState = rememberButtonState(),
    generateArchiveButtonState: ButtonState = rememberButtonState()
) = RibbonState(addButtonState, removeButtonState, generateArchiveButtonState)


data class RibbonState(
    val addButtonState: ButtonState,
    val removeButtonState: ButtonState,
    val generateArchiveButtonState: ButtonState
)