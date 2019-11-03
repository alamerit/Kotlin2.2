package ru.geekbrains.gb_kotlin.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import ru.geekbrains.gb_kotlin.data.NotesRepository
import ru.geekbrains.gb_kotlin.data.entity.Note
import ru.geekbrains.gb_kotlin.data.model.NoteResult
import ru.geekbrains.gb_kotlin.ui.base.BaseViewModel

class MainViewModel : BaseViewModel<List<Note>?, MainViewState>() {

    private val noteObserver = Observer<NoteResult> {
        it ?: return@Observer

        when(it){
            is NoteResult.Success<*> -> {
                viewStateLiveData.value = MainViewState(notes = it.data as? List<Note>)
            }
            is NoteResult.Error -> {
                viewStateLiveData.value = MainViewState(error = it.error)
            }
        }
    }

    private val repositoryNotes = NotesRepository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(noteObserver)
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData

    override fun onCleared() {
        repositoryNotes.removeObserver(noteObserver)
        super.onCleared()
    }
}