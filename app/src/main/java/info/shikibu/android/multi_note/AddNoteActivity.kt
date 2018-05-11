package info.shikibu.android.multi_note

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.realm.Realm
import kotlinx.android.synthetic.main.form_note.*

class AddNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        form_save_button.setOnClickListener {
            saveAddData()
            finish()
        }
    }

    private fun saveAddData() {
        Realm.getDefaultInstance().use { realm ->
            val note = Note()
            if(!realm.isEmpty) {
                note.id = realm.where(Note::class.java).max("id")!!.toLong() + 1
            }

            note.title  = form_title.text.toString()
            note.detail = form_detail.text.toString()

            realm.executeTransaction {
                it.copyToRealmOrUpdate(note)
            }

            realm.where(Note::class.java).findAll().forEach {
                Log.d("REALM", "${it.id}, ${it.detail}")
            }
        }

    }
}
