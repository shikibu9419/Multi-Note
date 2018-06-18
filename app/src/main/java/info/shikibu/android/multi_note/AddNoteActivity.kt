package info.shikibu.android.multi_note

import android.app.DatePickerDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import info.shikibu.android.multi_note.databinding.ActivityAddNoteBinding
import io.realm.Realm
import kotlinx.android.synthetic.main.form_note.*
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private val note = Note()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)
        binding.viewModel = AddNoteActivityViewModel()

        // setContentView(R.layout.activity_add_note)

        // Date init
        Calendar.getInstance().time.let { now ->
//            note.startDate = now
//            note.finishDate = now
            form_start_date.text = SDF.format(now)
            form_finish_date.text = SDF.format(now)
        }

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

        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, day ->
            if (columnName == "startDate") {
//                note.startDate = SDF.parse("$year/${month + 1}/$day")
                form_start_date.text = String.format(Locale.JAPAN, "%d/%d/%d", year, month + 1, day)
            } else {
//                note.finishDate = SDF.parse("$year/${month + 1}/$day")
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
//                createdAt = Calendar.getInstance().time
//                updatedAt = Calendar.getInstance().time
            }

            realm.executeTransaction {
                it.copyToRealmOrUpdate(note)
            }
        }
        finish()
    }

    companion object {
        val SDF = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN)
    }
}
