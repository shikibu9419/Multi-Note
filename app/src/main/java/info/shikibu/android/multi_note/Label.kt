package info.shikibu.android.multi_note

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Label(
        @PrimaryKey
        var id: Long = 0,
        var name: String = "",
        var colorCode: String = ""
) : RealmObject() {}
