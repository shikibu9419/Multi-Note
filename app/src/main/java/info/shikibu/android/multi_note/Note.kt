package info.shikibu.android.multi_note

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Note(
        @PrimaryKey var id: Long = 0,
        var title: String = "",
        var detail: String = "",
        var isDone: Boolean? = null
): RealmObject() {}