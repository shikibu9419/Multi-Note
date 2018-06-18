package info.shikibu.android.multi_note

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.shikibu.android.multi_note.databinding.FragmentDetailBinding
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_detail.*
import java.text.SimpleDateFormat
import java.util.*

class DetailFragment: Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private lateinit var mRealm: Realm

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRealm = Realm.getDefaultInstance()

        val noteId = arguments.getLong("NOTE_ID")

        mRealm.where(Note::class.java)
                .equalTo("id", noteId)
                .findFirst()
                ?.apply {
                    // toolbar.title = title
                    detail_text.text = detail
//                    start_date_text.text = SDF.format(startDate)
//                    finish_date_text.text = SDF.format(finishDate)
//                    created_at_text.text = SDF.format(createdAt)
//                    updated_at_text.text = SDF.format(updatedAt)
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

    companion object {
        val SDF = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN)
    }
}