package info.shikibu.android.multi_note

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.form_note.*
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {

    private val note = Note()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        // Date init
        val nowDate = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN)
        note.startDate = nowDate
        note.finishDate = nowDate
        form_start_date.text = sdf.format(nowDate)
        form_finish_date.text = sdf.format(nowDate)

        // setOnClickListener
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
        val nowYear = c.get(Calendar.YEAR)
        val nowMonth = c.get(Calendar.MONTH)
        val nowDay = c.get(Calendar.DAY_OF_MONTH)
        val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN)

        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, day ->
            if (columnName == "startDate") {
                note.startDate = sdf.parse("$year/${month + 1}/$day")
                form_start_date.text = String.format(Locale.JAPAN, "%d/%d/%d", year, month + 1, day)
            } else {
                note.finishDate = sdf.parse("$year/${month + 1}/$day")
                form_finish_date.text = String.format(Locale.JAPAN, "%d/%d/%d", year, month + 1, day)
            }
        }, nowYear, nowMonth, nowDay).show()
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
