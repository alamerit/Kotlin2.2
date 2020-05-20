package ru.geekbrains.gb_kotlin.ui.note

import android.support.annotation.VisibleForTesting
import kotlinx.coroutines.launch
import ru.geekbrains.gb_kotlin.data.NotesRepository
import ru.geekbrains.gb_kotlin.data.entity.Note
import ru.geekbrains.gb_kotlin.ui.base.BaseViewModel
import timber.log.Timber

class NoteViewModel(private val notesRepository: NotesRepository) : BaseViewModel<NoteData>() {

    private val currentNote: Note?
        get() = getViewState().poll()?.note

    fun save(note: Note) {
        setData(NoteData(note = note))
    }

    @VisibleForTesting
    public override fun onCleared() {
        launch {
            currentNote?.let { notesRepository.saveNote(it) }
            super.onCleared()
        }
    }

    fun loadNote(noteId: String) {
        Timber.d("Before launch")
        launch {
            try {
                Timber.d("Before getNoteById")
                notesRepository.getNoteById(noteId).let {
                    setData(NoteData(note = it))
                }
                Timber.d("After getNoteById")
            } catch (e: Throwable) {
                setError(e)
            }
        }

        Timber.d("After launch")
    }

    fun deleteNote() {
        launch {
            try {
                currentNote?.let { notesRepository.deleteNote(it.id) }
                setData(NoteData(isDeleted = true))
            } catch (e: Throwable) {
                setError(e)
            }
        }
    }
}