package ru.geekbrains.gb_kotlin.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.geekbrains.gb_kotlin.R
import ru.geekbrains.gb_kotlin.ui.note.NoteActivity

class MainActivity : AppCompatActivity() {

    lateinit var adapter: NotesRVAdapter
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesRVAdapter{
            NoteActivity.start(this,it)
        }
        rv_notes.adapter = adapter

        viewModel.viewState().observe(this, Observer { viewState ->
            viewState?.let { adapter.notes = it.notes }
        })
        fab.setOnClickListener{
            NoteActivity.start(this)

        }


    }

}
