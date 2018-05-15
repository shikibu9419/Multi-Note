package info.shikibu.android.multi_note

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_detail.*
import java.text.SimpleDateFormat
import java.util.*

class DetailFragment: Fragment() {

    private lateinit var mRealm: Realm

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRealm = Realm.getDefaultInstance()

        val noteId = arguments.getLong("NOTE_ID")
        val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN)

        mRealm.where(Note::class.java)
                .equalTo("id", noteId)
                .findFirst()
                ?.let { note ->
                    detail_text.text = note.detail

                    // Todo: Refactoring
                    if (start_date_text.text.isNotBlank()) {
                        start_date_text.text = sdf.format(note.startDate)
                    }
                    if (finish_date_text.text.isNotBlank()) {
                        finish_date_text.text = sdf.format(note.finishDate)
                    }
                }
    }

    override fun onDestroy() {
        mRealm.close()
        super.onDestroy()
    }

    fun createInstance(id: Long): DetailFragment {
        val fragment = DetailFragment()
        val args = Bundle()

        args.putLong("NOTE_ID", id)
        fragment.arguments = args
        return fragment
    }
}