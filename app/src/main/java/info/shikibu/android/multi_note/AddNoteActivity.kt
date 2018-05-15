package info.shikibu.android.multi_note

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.DatePicker
import io.realm.Realm
import kotlinx.android.synthetic.main.form_note.*
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        form_start_date.text = String.format(Locale.JAPAN, "%d", System.currentTimeMillis())
        form_finish_date.text = String.format(Locale.JAPAN, "%d", System.currentTimeMillis())

        form_start_date.setOnClickListener {
            DatePick().show(supportFragmentManager, "DatePick")
        }

        form_finish_date.setOnClickListener {
            DatePick().show(supportFragmentManager, "DatePick")
        }

        form_save_button.setOnClickListener {
            saveAddData()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        // Todo: Fix
        form_start_date.text = String.format(Locale.JAPAN, "%d/%d/%d", year, month + 1, day)
        form_finish_date.text = String.format(Locale.JAPAN, "%d/%d/%d", year, month + 1, day)
    }

    private fun saveAddData() {
        Realm.getDefaultInstance().use { realm ->
            Note().apply {
                val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN)

                if (!realm.isEmpty) {
                    id = realm.where(Note::class.java).max("id")!!.toLong() + 1
                }

                title = form_title.text.toString()
                detail = form_detail.text.toString()

                // Todo: String (form) -> Date
                startDate = sdf.parse(form_start_date.text.toString())
                finishDate = sdf.parse(form_finish_date.text.toString())

                realm.executeTransaction {
                    it.copyToRealmOrUpdate(this)
                }
            }
        }
        finish()
    }
}
