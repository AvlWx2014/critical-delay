package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.TooltipPlacement
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import ui.state.ButtonState
import ui.state.RibbonState
import ui.state.rememberRibbonState


@Composable
fun Ribbon(
    ribbonState: RibbonState = rememberRibbonState(),
) {
    val borderColor by mutableStateOf(MaterialTheme.colors.onBackground)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawWithContent {
                // shoutout to this StackOverflow post for the helpful drawing code:
                // https://stackoverflow.com/questions/68592618/how-to-add-border-on-bottom-only-in-jetpack-compose
                drawContent()
                clipRect {
                    val stroke = 1f
                    val y = size.height - stroke
                    drawLine(
                        color = borderColor,
                        strokeWidth = stroke,
                        cap = StrokeCap.Square,
                        start = Offset.Zero.copy(y = y),
                        end = Offset(x = size.width, y = y)
                    )
                }
            }
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ButtonGroup(modifier = Modifier.weight(2f), ribbonState)
        Box(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ButtonGroup(modifier: Modifier = Modifier, ribbonState: RibbonState) {
    Row(
        modifier = modifier.then(Modifier.padding(8.dp)),
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DoTheThingButton(ribbonState.addButtonState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DoTheThingButton(buttonState: ButtonState) {
    // Helpful tooltip examples:
    // https://github.com/JetBrains/compose-jb/tree/master/tutorials/Desktop_Components#tooltips
    TooltipArea(
        tooltip = { TooltipContent(buttonState.onHover) },
        delayMillis = 500,
        tooltipPlacement = TooltipPlacement.ComponentRect(
            anchor = Alignment.BottomCenter,
            alignment = Alignment.BottomEnd
        )
    ) {
        IconButton(
            enabled = buttonState.enableWhen(),
            modifier = Modifier
                .height(30.dp)
                .width(30.dp),
            onClick = buttonState.onClick
        ) {
            Icon(Icons.Filled.Refresh, "Do the Thing", modifier = Modifier.fillMaxSize())
        }
    }
}
