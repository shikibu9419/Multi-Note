package info.shikibu.android.multi_note

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

internal class ListNoteAdapter(data: OrderedRealmCollection<Note>)
    : RealmRecyclerViewAdapter<Note, ListNoteAdapter.ViewHolder>(data, true) {

    lateinit var listener: OnItemClickListener

    init {
        setHasStableIds(true)
    }

    override fun getItemId(index: Int): Long {
        return getItem(index)!!.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        setOnItemClickListener(listener)
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_note, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { note ->
            holder.data = note
            holder.title.text = note.title
            holder.detail.text = note.detail
            holder.itemRow.setOnClickListener { listener.onClick(it, note.id) }
        }
    }

    interface OnItemClickListener {
        fun onClick(view: View, id: Long)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var detail: TextView
        var itemRow: RelativeLayout
        var data: Note? = null

        init {
            itemRow = view.findViewById(R.id.item_row)
            title = view.findViewById(R.id.note_title)
            detail = view.findViewById(R.id.note_detail)
        }
    }
}
