package info.shikibu.android.multi_note

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

internal class ListNoteAdapter(data: OrderedRealmCollection<Note>)
    : RealmRecyclerViewAdapter<Note, ListNoteAdapter.ViewHolder>(data, true) {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(index: Int): Long {
        return getItem(index)!!.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_note, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.data = item
        holder.title.text = item?.title
        holder.detail.text = item?.detail
    }

    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var detail: TextView
        var data: Note? = null

        init {
            title = view.findViewById(R.id.note_title)
            detail = view.findViewById(R.id.note_detail)
        }
    }
}
