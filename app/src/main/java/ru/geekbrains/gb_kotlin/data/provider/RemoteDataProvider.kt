package ru.geekbrains.gb_kotlin.data.provider

import android.arch.lifecycle.LiveData
import ru.geekbrains.gb_kotlin.data.entity.Note
import ru.geekbrains.gb_kotlin.data.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note : Note): LiveData<NoteResult>
}