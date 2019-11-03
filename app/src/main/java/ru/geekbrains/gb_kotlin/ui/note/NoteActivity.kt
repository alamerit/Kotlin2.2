package ru.geekbrains.gb_kotlin.ui.note

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_note.*
import ru.geekbrains.gb_kotlin.R
import ru.geekbrains.gb_kotlin.data.entity.Note
import ru.geekbrains.gb_kotlin.ui.base.BaseActivity
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : BaseActivity<Note?, NoteViewState>() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"
        private const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"
        fun start(context: Context, noteId: String? = null) = Intent(context, NoteActivity::class.java).run {
            putExtra(EXTRA_NOTE, noteId)
            context.startActivity(this)
        }
    }

    private var note: Note? = null
    override val layoutRes = R.layout.activity_note
    override val viewModel: NoteViewModel by lazy {
        ViewModelProviders.of(this).get(NoteViewModel::class.java)
    }

    private val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            saveNote()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val noteId = intent.getStringExtra(EXTRA_NOTE)

        noteId?.let {
            viewModel.loadNote(it)
        } ?: let {
            supportActionBar?.title = getString(R.string.new_note_title)
        }

        initView()
    }

    override fun renderData(data: Note?) {
        this.note = data

        supportActionBar?.title = if (note != null) {
            SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(note!!.lastChanged)
        } else {
            getString(R.string.new_note_title)
        }

        initView()
    }

    private fun initView() {
        et_title.removeTextChangedListener(textChangeListener)
        et_body.removeTextChangedListener(textChangeListener)

        if (note != null) {
            et_title.setText(note?.title ?: "")
            et_body.setText(note?.text ?: "")
            val color = when (note!!.color) {
                Note.Color.WHITE -> R.color.white
                Note.Color.YELLOW -> R.color.yellow
                Note.Color.GREEN -> R.color.green
                Note.Color.BLUE -> R.color.blue
                Note.Color.RED -> R.color.red
                Note.Color.VIOLET -> R.color.violet
            }

            toolbar.setBackgroundColor(resources.getColor(color))
        }

        et_title.addTextChangedListener(textChangeListener)
        et_body.addTextChangedListener(textChangeListener)
    }

    fun saveNote() {
        if (et_title.text == null || et_title.text!!.length < 3) return

        note = note?.copy(
            title = et_title.text.toString(),
            text = et_body.text.toString(),
            lastChanged = Date()
        ) ?: Note(UUID.randomUUID().toString(), et_title.text.toString(), et_body.text.toString())

        note?.let { viewModel.save(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }
}