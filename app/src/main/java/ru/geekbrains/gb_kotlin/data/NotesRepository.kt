package ru.geekbrains.gb_kotlin.data

import ru.geekbrains.gb_kotlin.data.entity.Note

object NotesRepository {

    var notes : List<Note> = listOf(
        Note(
            "Юля",
            "Текст первой заметки. Не очень длинный, но очень интересный",
            0xfff06292.toInt()
        ),
        Note(
            "Rt",
            "Текст второй заметки. Не очень длинный, но очень интересный",
            0xff9575cd.toInt()
        ),
        Note(
            "Lena",
            "Текст третьей заметки. Не очень длинный, но очень интересный",
            0xff64b5f6.toInt()
        ),
        Note(
            "Katya",
            "Текст четвертой заметки. Не очень длинный, но очень интересный",
            0xff4db6ac.toInt()
        ),
        Note(
            "Таня",
            "Текст пятой заметки. Не очень длинный, но очень интересный",
            0xffb2ff59.toInt()
        ),
        Note(
            "Мария",
            "Текст шестой заметки. Не очень длинный, но очень интересный",
            0xffffeb3b.toInt()
        )
    )

    private set
}