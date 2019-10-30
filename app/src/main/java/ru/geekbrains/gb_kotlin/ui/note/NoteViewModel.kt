package ru.geekbrains.gb_kotlin.ui.note

import android.arch.lifecycle.ViewModel
import ru.geekbrains.gb_kotlin.data.NotesRepository
import ru.geekbrains.gb_kotlin.data.entity.Note

class NoteViewModel : ViewModel() {

    private  var pendingNote : Note?= null

    fun save (note:Note){
        pendingNote = note
    }

    override fun onCleared() {
         pendingNote?.let {
             NotesRepository.saveNote(it)
         }
    }
}