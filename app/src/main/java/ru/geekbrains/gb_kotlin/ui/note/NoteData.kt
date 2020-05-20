package ru.geekbrains.gb_kotlin.ui.note

import ru.geekbrains.gb_kotlin.data.entity.Note

data class NoteData(val isDeleted: Boolean = false, val note: Note? = null)