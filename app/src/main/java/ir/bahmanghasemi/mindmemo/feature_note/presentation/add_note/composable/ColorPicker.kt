package ir.bahmanghasemi.mindmemo.feature_note.presentation.add_note.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import ir.bahmanghasemi.mindmemo.feature_note.presentation.util.NoteColor

@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    selectedColor: Int,
    onColorChange: (Int) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        NoteColor.entries.forEach {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .shadow(elevation = 5.dp, shape = CircleShape)
                    .background(it.color)
                    .border(
                        width = 2.dp,
                        shape = CircleShape,
                        color = if (selectedColor == it.color.toArgb()) Color(
                            ColorUtils.blendARGB(
                                it.color.toArgb(),
                                Color.Black.toArgb(),
                                0.5f
                            )
                        )
                        else Color.Transparent
                    )
                    .clickable { onColorChange(it.color.toArgb()) }
            )
        }
    }
}