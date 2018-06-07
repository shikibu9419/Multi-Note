package info.shikibu.android.multi_note

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class Note(
        @PrimaryKey
        var id: Long = 0,
        var title: String = "",
        var detail: String = "",
        var priority: Int = 0,
        var createdAt: Calendar? = null,
        var updatedAt: Calendar? = null,
        var labels: RealmList<Label>? = null,
        var todo: Todo? = null
) : RealmObject() {
}