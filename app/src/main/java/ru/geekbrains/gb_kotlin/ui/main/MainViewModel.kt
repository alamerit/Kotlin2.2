package ru.geekbrains.gb_kotlin.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ru.geekbrains.gb_kotlin.data.NotesRepository.notes

class MainViewModel : ViewModel() {

    private val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData()

    init {
        viewStateLiveData.value = MainViewState(notes)
    }

    fun viewState(): LiveData<MainViewState> =  viewStateLiveData
}