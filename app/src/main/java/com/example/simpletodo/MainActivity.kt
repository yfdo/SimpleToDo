package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

//deal with user interactions

class MainActivity : AppCompatActivity() {
    //list of tasks
    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object: TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //remove the item from the list
                listOfTasks.removeAt(position)
                //notify adapter of the change
                adapter.notifyDataSetChanged()

                //save items to file
                saveItems()
            }
        }

        //instead of hard-coding tasks here as before
        //populate listOfItems from file
        loadItems()

        //look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        //attach the adapter to the recyclerView
        recyclerView.adapter = adapter
        //set layout manager to position the items
        //linearly
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        //set up the button and input field, so that the user enter a task and add it to the list
        findViewById<Button>(R.id.addTaskButton).setOnClickListener {
            //grab the text the user has inputted into @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            //add the string to the list of tasks
            listOfTasks.add(userInputtedTask)
            //notify the adapter of the data update
            //add new element at the end of task list
            adapter.notifyItemInserted(listOfTasks.size-1)

            //clear out addTaskField input field
            inputTextField.setText("")

            //save task to file
            saveItems()
        }
    }

    //save the data the user has inputted/current status of the list when user exit the app
    //by writing and reading from a file

    //get the data file we need
    fun getDataFile(): File {
        //every line represent a task in listOfTasks
        return File(filesDir, "data.txt")
    }

    //load the item by reading every line in the data file
    fun loadItems(){
        try{
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException){
            //if file doesn't exist?
            ioException.printStackTrace()
        }
    }

    //save items by writing them into the data file
    fun saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }
}