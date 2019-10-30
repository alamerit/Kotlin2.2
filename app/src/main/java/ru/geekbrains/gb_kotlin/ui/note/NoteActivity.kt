package ru.geekbrains.gb_kotlin.ui.note

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_note.*
import ru.geekbrains.gb_kotlin.R
import ru.geekbrains.gb_kotlin.data.entity.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : AppCompatActivity() {

    companion object{

        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.Note "
        private  const val  DATE_Time_Format = "dd.MM.yy HH:mm"
        fun start(context: Context,note: Note?= null ){
            val intent = Intent (context,NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE,note)
            context.startActivity(intent)
        }
    }

    private var note: Note?  = null
    lateinit var viewModel: NoteViewModel

    val textChangeListner = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            savengNote()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        note = intent.getParcelableExtra(EXTRA_NOTE)
        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        supportActionBar?.title = if (note != null){
            SimpleDateFormat(
                DATE_Time_Format,
                Locale.getDefault()
            ).format (note!!.lastChanget)
        }else{
            getString(R.string.new_note_title)
        }
        initView()
    }

    private fun initView() {
        et_title.removeTextChangedListener(textChangeListner)
        et_body.removeTextChangedListener(textChangeListner)
        if (note != null){
            et_title.setText(note?.text ?:"")
            et_body.setText(note?.text ?:"")
            val color = when(note!!.color) {
                    Note.Color.WHITE -> R.color.white
                    Note.Color.YELLOW -> R.color.yellow
                    Note.Color.GREEN -> R.color.green
                    Note.Color.BLUE -> R.color.blue
                    Note.Color.RED -> R.color.red
                    Note.Color.VIOLET -> R.color.violet
            }

            toolbar.setBackgroundColor(resources.getColor(color))
        }
        et_title.addTextChangedListener(textChangeListner)
        et_body.addTextChangedListener(textChangeListner)
    }

    fun savengNote(){
        if (et_title.text == null || et_title.text!!.length < 3) return

        note = note?.copy(
            title = et_title.text.toString(),
            text = et_body.text.toString(),
            lastChanget = Date()
        ) ?: Note(UUID.randomUUID().toString(),et_title.text.toString(),et_body.text.toString())

    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item!!.itemId) {
        android.R.id.home ->{
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}