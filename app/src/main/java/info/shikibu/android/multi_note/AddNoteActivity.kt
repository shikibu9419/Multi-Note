package info.shikibu.android.multi_note

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.form_note.*
import java.util.*

class AddNoteActivity : AppCompatActivity() {

    private val note = Note()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        form_start_date.text = String.format(Locale.JAPAN, "%d", System.currentTimeMillis())
        form_finish_date.text = String.format(Locale.JAPAN, "%d", System.currentTimeMillis())

        form_start_date.setOnClickListener {
            onDateFormClickListener("startDate")
        }

        form_finish_date.setOnClickListener {
            onDateFormClickListener("finishDate")
        }

        form_save_button.setOnClickListener {
            saveAddData()
        }
    }

    private fun onDateFormClickListener(columnName: String) {
        val c = Calendar.getInstance()
        val cYear = c.get(Calendar.YEAR)
        val cMonth = c.get(Calendar.MONTH)
        val cDay = c.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, day ->
            if (columnName == "startDate") {
                note.startDate = Date(year, month + 1, day)
                form_start_date.text = String.format(Locale.JAPAN, "%d/%d/%d", year, month + 1, day)
            } else {
                note.finishDate = Date(year, month + 1, day)
                form_finish_date.text = String.format(Locale.JAPAN, "%d/%d/%d", year, month + 1, day)
            }
        }, cYear, cMonth, cDay).show()
    }

    private fun saveAddData() {
        Realm.getDefaultInstance().use { realm ->
            note.apply {
                if (!realm.isEmpty) {
                    id = realm.where(Note::class.java).max("id")!!.toLong() + 1
                }
                title = form_title.text.toString()
                detail = form_detail.text.toString()
            }

            realm.executeTransaction {
                it.copyToRealmOrUpdate(note)
            }
        }
        finish()
    }
}
