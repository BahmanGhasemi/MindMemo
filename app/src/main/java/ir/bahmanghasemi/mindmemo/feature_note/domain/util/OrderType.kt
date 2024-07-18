package ir.bahmanghasemi.mindmemo.feature_note.domain.util

abstract class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}