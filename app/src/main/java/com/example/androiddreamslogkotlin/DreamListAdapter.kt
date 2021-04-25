package com.example.androiddreamslogkotlin


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class DreamListAdapter : ListAdapter<Dream, DreamListAdapter.DreamViewHolder>(DreamComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DreamViewHolder {
        return DreamViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DreamViewHolder, position: Int) {
        val currentDream = getItem(position)
        holder.bindText(currentDream.title, holder.textViewTitle)
        holder.bindText(currentDream.date, holder.textViewId)
        holder.textViewTitle.setOnClickListener{

            val intent = Intent(holder.textViewTitle.context, ViewActivity::class.java)
            intent.putExtra("id", currentDream.id)
            holder.textViewTitle.context.startActivity(intent)

        }
    }

    class DreamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textView_title)
        val textViewId: TextView = itemView.findViewById(R.id.textView_id)

        fun bindText(text:String?, textView:TextView) {
            textView.text = text
        }

        companion object{
            fun create (parent: ViewGroup) : DreamViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dream, parent, false)
                return DreamViewHolder(view)
            }
        }
    }

    class DreamComparator : DiffUtil.ItemCallback<Dream>(){

        override fun areContentsTheSame(oldItem: Dream, newItem: Dream): Boolean {
            return oldItem.title == newItem.title
        }
        override fun areItemsTheSame(oldItem: Dream, newItem: Dream): Boolean {
            return oldItem === newItem
        }



    }


}