package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.DeterminateProgress
import model.IndeterminateProgress
import model.LabeledProgress
import model.ratio
import ui.state.ProgressIndicatorState

@Composable
@Preview
fun BottomTray(
    bottomStartContent: @Composable () -> Unit = {},
    progressIndicatorState: ProgressIndicatorState
) {
    val borderColor by mutableStateOf(MaterialTheme.colors.onBackground)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(color=MaterialTheme.colors.background)
            .drawWithContent {
                // shoutout to this StackOverflow post for the helpful drawing code:
                // https://stackoverflow.com/questions/68592618/how-to-add-border-on-bottom-only-in-jetpack-compose
                drawContent()
                clipRect  {
                    val stroke = 1f
                    drawLine(
                        color = borderColor,
                        strokeWidth = stroke,
                        cap = StrokeCap.Square,
                        start = Offset.Zero,
                        end = Offset(x = size.width, y = 0f)
                    )
                }
            }
    ) {
        BottomTrayContent(bottomStartContent, progressIndicatorState)
    }
}

@Composable
private fun RowScope.BottomTrayContent(
    bottomStartContent: @Composable () -> Unit,
    progressIndicatorState: ProgressIndicatorState
) {
    Box(
        modifier = Modifier
            .weight(1.0f)
            .fillMaxSize()
            .padding(2.dp)
    ) {
        bottomStartContent()
    }

    Box(
        modifier = Modifier
            .weight(1.0f)
            .fillMaxSize()
            .padding(
                start = 2.dp,
                top = 2.dp,
                end = 4.dp,
                bottom = 2.dp
            )
    ) {
        ProgressIndicatorContent(progressIndicatorState, modifier = Modifier.align(Alignment.CenterEnd))
    }
}


@Composable
private fun ProgressIndicatorContent(progressIndicatorState: ProgressIndicatorState, modifier: Modifier = Modifier) {
    val show = progressIndicatorState.show
    if (show) {
        when (progressIndicatorState.progress) {
            is LabeledProgress -> LabeledProgressIndicator(progressIndicatorState, modifier = modifier)
            else -> ProgressIndicator(progressIndicatorState, modifier = modifier)
        }
    }
}


@Composable
private fun ProgressIndicator(progressIndicatorState: ProgressIndicatorState, modifier: Modifier = Modifier) {
    when (val progress = progressIndicatorState.progress) {
        is IndeterminateProgress -> LinearProgressIndicator(modifier = modifier)
        is DeterminateProgress -> {
            val float by remember(progress) { mutableStateOf(progress.ratio) }
            LinearProgressIndicator(float, modifier)
        }
    }
}


@Composable
private fun LabeledProgressIndicator(
    progressIndicatorState: ProgressIndicatorState,
    modifier: Modifier = Modifier
) {
    val progress = progressIndicatorState.progress
    check(progress is LabeledProgress) {
        "LabeledProgressIndicator called without LabeledProgress"
    }

    val label by remember(progress) { mutableStateOf(progress.label) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        Label(label, modifier = Modifier.alignByBaseline().align(Alignment.CenterVertically))
        when (progress) {
            is IndeterminateProgress -> LinearProgressIndicator(modifier = Modifier.align(Alignment.CenterVertically))
            is DeterminateProgress -> {
                val float by remember(progress) { mutableStateOf(progress.ratio) }
                LinearProgressIndicator(float, Modifier.align(Alignment.CenterVertically))
            }
        }
    }
}

@Composable
private fun Label(label: String, modifier: Modifier = Modifier) {
    Text(label, fontSize = 14.sp, modifier = modifier)
}