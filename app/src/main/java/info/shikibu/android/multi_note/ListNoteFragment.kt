package info.shikibu.android.multi_note

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.realm.Realm

class ListNoteFragment : Fragment() {

    private lateinit var mRealm: Realm

    private inner class TouchHelperCallback internal constructor() : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

        override fun onMove(recyclerView: RecyclerView,
                            viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        }

        override fun isLongPressDragEnabled(): Boolean {
            return true
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_note, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRealm = Realm.getDefaultInstance()
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
        val resultData = mRealm.where(Note::class.java).findAll()
        val adapter = ListNoteAdapter(resultData)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        val touchHelperCallback = TouchHelperCallback()
        val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        adapter.setOnItemClickListener(object : ListNoteAdapter.OnItemClickListener {
            override fun onClick(view: View, id: Long) {
                Toast.makeText(activity, "Item ID: $id", Toast.LENGTH_LONG).show()
            }
        })
    }

//    private fun deleteItem(id: Long) {
//        synchronized(lock = Object()) {
//            val item = resultData.where().equalTo("id", id).findFirst()
//            mRealm.executeTransaction {
//                item?.deleteFromRealm()
//            }
//        }
//    }
}
