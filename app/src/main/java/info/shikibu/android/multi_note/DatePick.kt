package info.shikibu.android.multi_note

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import java.util.*


class DatePick : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(activity, activity as AddNoteActivity, year, month, day)
    }

    override fun onDateSet(view: android.widget.DatePicker, year: Int,
                           monthOfYear: Int, dayOfMonth: Int) {
    }

}
