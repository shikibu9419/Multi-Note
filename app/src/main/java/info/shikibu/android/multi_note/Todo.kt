package info.shikibu.android.multi_note

import android.provider.CalendarContract
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class Todo(
        @PrimaryKey
        var id: Long = 0,
        var isDone: Boolean = false,
        var startDate: Calendar? = null,
        var finishDate: Calendar? = null,
        var reminders: CalendarContract.Reminders? = null
) : RealmObject() {}

