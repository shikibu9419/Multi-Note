package info.shikibu.android.multi_note

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmRecyclerViewAdapter

internal class ListNoteAdapter(data: OrderedRealmCollection<Note>)
    : RealmRecyclerViewAdapter<Note, ListNoteAdapter.ViewHolder>(data, true) {

    private lateinit var mListener: OnItemClickListener

    init {
        setHasStableIds(true)
    }

    override fun getItemId(index: Int): Long {
        return getItem(index)!!.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        setOnItemClickListener(mListener)
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_note, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            getItem(position)?.let { note ->
                data = note
                title.text = note.title
                detail.text = note.detail
                itemRow.setOnClickListener { mListener.onClick(it, note.id) }
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(view: View, id: Long)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mListener = listener
    }

    fun deleteItem(id: Long) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                realm.where(Note::class.java)
                        .equalTo("id", id)
                        .findFirst()
                        ?.deleteFromRealm()

                notifyDataSetChanged()
            }
        }
    }

    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var detail: TextView
        var itemRow: RelativeLayout
        var data: Note? = null

        init {
            itemRow = view.findViewById(R.id.item_row)
            title = view.findViewById(R.id.item_title)
            detail = view.findViewById(R.id.item_detail)
        }
    }
}
