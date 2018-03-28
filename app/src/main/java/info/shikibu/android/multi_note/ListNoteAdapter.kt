package info.shikibu.android.multi_note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class ListNoteAdapter(realmResults: OrderedRealmCollection<Note>)
    : RealmBaseAdapter<Note>(realmResults), ListAdapter {

    private class ViewHolder(itemView: View) {
        internal var listTitle: TextView = itemView.findViewById<View>(R.id.list_title) as TextView
        internal var listDetail: TextView = itemView.findViewById<View>(R.id.list_detail) as TextView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var view: View? = convertView
        val holder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_note, parent, false)

            holder = ViewHolder(view)

            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        holder.listTitle.text = getItem(position)?.title
        holder.listDetail.text = getItem(position)?.detail

        return view
    }
}
