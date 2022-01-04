package com.example.simpletodo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * A bridge that tells the recyclerView
 * how to display the data we give it
 */

class TaskItemAdapter(val listOfItems: List<String>, val longClickListener: OnLongClickListener) : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>(){

    interface OnLongClickListener{
        fun onItemLongClicked(position: Int){
        }
    }

    //usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //specify how to layout each element in recyclerView
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        //inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        //return a new holder instance
        return ViewHolder(contactView)
    }

    //involves populating data into the item through holder
    //here, populates what's in listOfTasks in the TextView variable in ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //get the data model based on position
        val item = listOfItems.get(position)
        //set the textView to whatever the string item is at specified position
        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //store references to elements in our layout view
        //for this project, layout view only has one textview so we grab that
        val textView: TextView
        init{
            textView = itemView.findViewById(android.R.id.text1)

            itemView.setOnLongClickListener{
                //when long click each task in recyclerView
                //here is calling listOfTasks by reference, so we must declare a fun somewhere else to actually make changes to the list (see top of this file)
                //call the longClickListener that was passed in
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }
}