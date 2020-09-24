package dev.dayvar.fetchmobileapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.dayvar.fetchmobileapplication.R
import dev.dayvar.fetchmobileapplication.models.Item
import kotlinx.android.synthetic.main.list.view.*
import kotlinx.android.synthetic.main.list_item.view.*

class ListAdapter(private val listTable: HashMap<Int, MutableList<Item>>): RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    val listIds = listTable.keys.toSortedSet().toMutableList()

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val listIdTextView = view.text_view_listId
        val itemHolderView = view.view_item_holder
        val headerLayout = view.header_layout
        val toggleImage = view.image_toggle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val listView = LayoutInflater.from(parent.context).inflate(R.layout.list,
        parent, false)

        return ListViewHolder(listView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val currentListId = listIds[position]
        holder.listIdTextView.text = "List ID: ${currentListId.toString()}"

        holder.headerLayout.setOnLongClickListener( View.OnLongClickListener {
            toggleVisibility(holder)
            true
        })

        holder.headerLayout.setOnClickListener( View.OnClickListener {
            if(holder.itemHolderView.visibility == View.GONE)
                toggleVisibility(holder)
        })

        holder.toggleImage.setOnClickListener( View.OnClickListener {
                toggleVisibility(holder)
        })

        holder.itemHolderView.visibility = View.VISIBLE
        val items = listTable.get(currentListId)!!.sortedBy { it.name }

        for (item in items){
            val itemView = LayoutInflater.from(holder.itemHolderView.context).inflate(R.layout.list_item,
                holder.itemHolderView, false)

            itemView.text_view_id.text = "ID: ${item.id.toString()}"
            itemView.text_view_name.text = "${item.name}"

            holder.itemHolderView.addView(itemView)
        }
    }

    override fun getItemCount(): Int {
        return listIds.size
    }

    fun toggleVisibility(holder: ListViewHolder){
        val itemHolderView = holder.itemHolderView
        if(itemHolderView.visibility == View.VISIBLE) {
            itemHolderView.visibility = View.GONE
            holder.toggleImage.setImageResource(R.drawable.ic_expand_more)
        }
        else {
            itemHolderView.visibility = View.VISIBLE
            holder.toggleImage.setImageResource(R.drawable.ic_expand_less)
        }
    }

}