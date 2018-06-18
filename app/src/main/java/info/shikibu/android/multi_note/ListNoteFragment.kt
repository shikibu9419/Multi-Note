package info.shikibu.android.multi_note

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.shikibu.android.multi_note.databinding.FragmentListNoteBinding
import io.realm.Realm
import io.realm.RealmResults

class ListNoteFragment : Fragment() {

    private lateinit var binding: FragmentListNoteBinding

    private lateinit var mRealm: Realm
    private lateinit var mAdapter: ListNoteAdapter
    private lateinit var mListener: ListNoteFragmentListener
    private lateinit var result: RealmResults<Note>

    interface ListNoteFragmentListener {
        fun onListItemClickListener(id: Long)
    }

    private inner class TouchHelperCallback internal constructor() : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

        override fun onMove(recyclerView: RecyclerView,
                            viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            // TODO: Implement delete function
            mAdapter.deleteItem(viewHolder.itemId)
        }

        override fun isLongPressDragEnabled(): Boolean {
            return true
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mListener = activity as ListNoteFragmentListener
        mRealm = Realm.getDefaultInstance()
        result = mRealm.where(Note::class.java).findAll()
        mAdapter = ListNoteAdapter(result)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentListNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setUpRecyclerView()
    }

    override fun onDestroy() {
        mRealm.close()
        super.onDestroy()
    }

    private fun setUpRecyclerView() {
        val recyclerView = view?.findViewById<View>(R.id.list_view_note) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        val touchHelperCallback = TouchHelperCallback()
        val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        mAdapter.setOnItemClickListener(object : ListNoteAdapter.OnItemClickListener {
            override fun onClick(view: View, id: Long) {
                mListener.onListItemClickListener(id)
            }
        })
    }
}
