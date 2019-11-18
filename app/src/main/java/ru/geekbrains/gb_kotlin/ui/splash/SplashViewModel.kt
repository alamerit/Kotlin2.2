package ru.geekbrains.gb_kotlin.ui.splash

import ru.geekbrains.gb_kotlin.data.NotesRepository
import ru.geekbrains.gb_kotlin.data.errors.NoAuthException
import ru.geekbrains.gb_kotlin.ui.base.BaseViewModel

class SplashViewModel(private val notesRepository: NotesRepository) :
    BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        notesRepository.getCurrentUser().observeForever {
            viewStateLiveData.value = if (it != null) {
                SplashViewState(anthenticated = true)
            } else {
                SplashViewState(error = NoAuthException())
            }
        }
    }

}