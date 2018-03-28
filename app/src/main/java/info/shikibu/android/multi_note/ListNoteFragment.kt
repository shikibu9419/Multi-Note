package info.shikibu.android.multi_note

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import io.realm.Realm

class ListNoteFragment : Fragment() {

    private lateinit var mRealm: Realm

    private fun listUpNote(view: View?) {
        mRealm = Realm.getDefaultInstance()

        val allData = mRealm.where(Note::class.java).findAll()
        val mListView = view?.findViewById<View>(R.id.list_view_note) as ListView

        mListView.adapter = ListNoteAdapter(allData)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_note, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listUpNote(view)
    }
}
