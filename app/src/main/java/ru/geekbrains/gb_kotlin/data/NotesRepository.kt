package ru.geekbrains.gb_kotlin.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import ru.geekbrains.gb_kotlin.data.entity.Note
import java.util.*

object NotesRepository {
    private val notesLiveData: MutableLiveData <List<Note>>
        get() {
            return notesLiveData
        }


    private var notes = mutableListOf(
        Note(
            UUID.randomUUID().toString(),
            "Юля",
            "Текст первой заметки. Не очень длинный, но очень интересный",
            color = Note.Color.WHITE
        ),
        Note(
            UUID.randomUUID().toString(),

            "Rt",
            "Текст второй заметки. Не очень длинный, но очень интересный",
            color = Note.Color.WHITE
        ),
        Note(
            UUID.randomUUID().toString(),

            "Lena",
            "Текст третьей заметки. Не очень длинный, но очень интересный",
            color = Note.Color.WHITE        ),
        Note(
            UUID.randomUUID().toString(),

            "Katya",
            "Текст четвертой заметки. Не очень длинный, но очень интересный",
            color = Note.Color.WHITE        ),
        Note(
            UUID.randomUUID().toString(),

            "Таня",
            "Текст пятой заметки. Не очень длинный, но очень интересный",
            color = Note.Color.WHITE        ),
        Note(
            UUID.randomUUID().toString(),
            "Мария",
            "Текст шестой заметки. Не очень длинный, но очень интересный",
            color = Note.Color.WHITE        )
    )
    init {
        notesLiveData.value = notes
    }

    fun getNotes():LiveData<List<Note>>{

           return notesLiveData
        }
    fun saveNote(note: Note){
        addOrReplace(note)
        notesLiveData.value = notes
    }

    private fun addOrReplace(note: Note){
        for (i in notes.indices) {
            if (notes[i] == note) {
                notes[i] = note
                return
            }
        }
        notes.add(note)

    }

}