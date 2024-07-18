package ir.bahmanghasemi.mindmemo.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val content: String,
    val color: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
