package ru.geekbrains.gb_kotlin.data.provider

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.Timber
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import ru.geekbrains.gb_kotlin.data.entity.Note
import ru.geekbrains.gb_kotlin.data.model.NoteResult

class FireStoreProvider : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
    }

    private val store = FirebaseFirestore.getInstance()
    private val notesReference = store.collection(NOTES_COLLECTION)

    override fun subscribeToAllNotes(): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()
        notesReference.addSnapshotListener { snapshot, e ->
            e?.let { result.value = NoteResult.Error(it) }
                ?: let {
                    snapshot?.let {
                        val notes = mutableListOf<Note>()
                        for (doc: QueryDocumentSnapshot in snapshot) {
                            notes.add(doc.toObject(Note::class.java))
                        }
                        result.value = NoteResult.Success(notes)
                    }
                }
        }

        return result
    }

    override fun getNoteById(id: String): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        notesReference.document(id).get()
            .addOnSuccessListener { snapshot ->
                result.value = NoteResult.Success(snapshot.toObject(Note::class.java))
            }.addOnFailureListener { result.value = NoteResult.Error(it) }

        return result
    }

    override fun saveNote(note: Note): LiveData<NoteResult> {
        val result = MutableLiveData<NoteResult>()

        notesReference.document(note.id)
            .set(note)
            .addOnSuccessListener { snapshot ->
                Timber.d { "Note $note is saved" }
                result.value = NoteResult.Success(note)
            }.addOnFailureListener {
                Timber.d { "Error saving note $note, message: ${it.message}" }
                result.value = NoteResult.Error(it)
            }

        return result
    }


}