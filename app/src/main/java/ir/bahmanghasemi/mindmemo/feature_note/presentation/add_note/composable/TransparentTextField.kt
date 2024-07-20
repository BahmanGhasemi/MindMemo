package ir.bahmanghasemi.mindmemo.feature_note.presentation.add_note.composable

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    hint: String,
    textStyle: TextStyle,
    isSingleLine: Boolean = false
) {
    BasicTextField(
        modifier = modifier,
        value = text,
        onValueChange = onTextChanged,
        singleLine = isSingleLine,
        decorationBox = { innerTextField ->
            if (text.isEmpty()) {
                Text(text = hint, color = Color.Gray, style = textStyle)
            }
            innerTextField()
        }
    )
}