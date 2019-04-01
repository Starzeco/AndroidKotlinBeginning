package com.example.secondapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.secondapp.row_models.FirstRow
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    companion object {
        const val ROW_ARRAY = "rowArray"
    }
    val rowList = ArrayList<FirstRow>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val simpleDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss")


        val tagsForRow = ArrayList<String>()
        tagsForRow.add("cosTam123")
        tagsForRow.add("JEszcze jeden")
        val rowToAdd = FirstRow("Boniek", "wtf", simpleDate.format(Date()), tagsForRow.joinToString())

        rowList.add(rowToAdd)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = CustomAdapter(rowList)

        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (recycler_view.adapter as CustomAdapter).removeItem(viewHolder)
            }

        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recycler_view)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean{
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if(id == R.id.add){

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if(outState != null){
            outState.putParcelableArrayList(ROW_ARRAY, rowList)
        }
    }

   override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if( savedInstanceState != null){
            val tempList: ArrayList<FirstRow> = savedInstanceState.getParcelableArrayList<FirstRow>(ROW_ARRAY)
            val range:Int = tempList.size-1
            for(i in 0..range){
                rowList.add(tempList[i])
            }
        }
    }
}
