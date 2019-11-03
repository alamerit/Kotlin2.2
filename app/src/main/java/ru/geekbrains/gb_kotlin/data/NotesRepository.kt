package ru.geekbrains.gb_kotlin.data

import ru.geekbrains.gb_kotlin.data.entity.Note
import ru.geekbrains.gb_kotlin.data.provider.FireStoreProvider
import ru.geekbrains.gb_kotlin.data.provider.RemoteDataProvider

object NotesRepository {

    private val remoteProvider: RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
}