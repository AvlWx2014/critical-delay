package ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

/**
 * Helpful tooltip examples from JetBrains:
 * https://github.com/JetBrains/compose-jb/tree/master/tutorials/Desktop_Components#tooltips
 */
@Composable
fun TooltipContent(text: String) {
    if (text.isNotEmpty()) {
        Surface(
            modifier = Modifier.shadow(4.dp),
            color = MaterialTheme.colors.primary,
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}