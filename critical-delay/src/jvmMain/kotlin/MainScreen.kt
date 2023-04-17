@file:JvmName("MainScreen")

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.BottomTray
import ui.Ribbon
import ui.state.ProgressIndicatorState
import ui.state.rememberButtonState
import ui.state.rememberProgressIndicatorState
import ui.state.rememberRibbonState
import viewmodel.MainScreenViewModel


@Composable
fun MainScreen(mainScreenViewModel: MainScreenViewModel) {
    val progressIndicatorState = rememberProgressIndicatorState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBarContent(mainScreenViewModel, progressIndicatorState) },
        content = { paddingValues -> ScaffoldContent(modifier = Modifier.padding(paddingValues)) },
        bottomBar = { BottomBarContent(progressIndicatorState) }
    )
}

@Composable
private fun TopBarContent(
    viewModel: MainScreenViewModel,
    progressIndicatorState: ProgressIndicatorState
) {
    val ribbonState = rememberRibbonState(
        addButtonState = rememberButtonState(onHover = "Zhu Li, do the thing!") {
            viewModel.doSomethingWithProgress(
                progressCallback = {
                    progressIndicatorState.show = true
                    progressIndicatorState.progress = it
                }
            ) {
                progressIndicatorState.show = false
            }
        }
    )

    Ribbon(ribbonState = ribbonState)
}

@Composable
private fun ScaffoldContent(modifier: Modifier = Modifier) {

}

@Composable
private fun BottomBarContent(progressIndicatorState: ProgressIndicatorState) {
    BottomTray(progressIndicatorState = progressIndicatorState)
}
