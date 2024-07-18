package ir.bahmanghasemi.mindmemo.feature_note.presentation.notes.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ir.bahmanghasemi.mindmemo.R
import ir.bahmanghasemi.mindmemo.feature_note.domain.util.NoteOrder
import ir.bahmanghasemi.mindmemo.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Ascending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DefaultRadioButton(text = stringResource(R.string.title), selected = noteOrder is NoteOrder.Title) {
                onOrderChange(NoteOrder.Title(noteOrder.orderType))
            }

            DefaultRadioButton(text = stringResource(R.string.date), selected = noteOrder is NoteOrder.Date) {
                onOrderChange(NoteOrder.Date(noteOrder.orderType))
            }

            DefaultRadioButton(text = stringResource(R.string.color), selected = noteOrder is NoteOrder.Color) {
                onOrderChange(NoteOrder.Color(noteOrder.orderType))
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DefaultRadioButton(
                text = stringResource(R.string.ascending),
                selected = noteOrder.orderType is OrderType.Ascending
            ) {
                onOrderChange(noteOrder.copy(OrderType.Ascending))
            }

            DefaultRadioButton(
                text = stringResource(R.string.descending),
                selected = noteOrder.orderType is OrderType.Descending
            ) {
                onOrderChange(noteOrder.copy(OrderType.Descending))
            }
        }
    }
}