package com.example.myportfolio.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Update
import com.example.myportfolio.R
import com.example.myportfolio.R.id
import com.example.myportfolio.model.Portfolio
import com.example.myportfolio.viewmodel.PortfolioViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_portfolio.*

open class MainActivity : AppCompatActivity(), PortfolioAdapter.PortfolioAdapterListener {
    private lateinit var portfolioViewModel: PortfolioViewModel
    private var actionModeCallback: ActionModeCallback? = null
    private var actionMode: ActionMode? = null
    private var mAdapter: PortfolioAdapter? = null
    private lateinit var updatePortfolio: String
    private lateinit var positionInt: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(id.recyclePortfolio)
        mAdapter = PortfolioAdapter(this, this)
        recyclerView.adapter = mAdapter
        actionModeCallback = ActionModeCallback()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        portfolioViewModel = ViewModelProvider(this).get(PortfolioViewModel::class.java)
        portfolioViewModel.allPortfolios.observe(this, Observer { portfolio ->
            portfolio?.let { mAdapter!!.setPortfolio(it)
            val portfoliosize = portfolio.size
                portfolioSize.text = portfoliosize.toString()
            }
           // fun getPortfolioId(position: Int): Int {
                for (i in portfolio){
                    Log.d("KEYINDEX", "${i.portfolioId}")
                   // return
                    i.portfolioId
                }
              //  return 0
           // }
        })

        floatingActionButton.setOnClickListener {
            val intent = Intent(this@MainActivity, NewPortfolio::class.java)
            startActivity(intent)
        }
            val itemTouchHelperCallback =
                object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.UP) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                      val deleteX =  myDeleteViewHolder(viewHolder)
                        Log.d("Id", "$deleteX")

                        Toast.makeText(this@MainActivity, "Deleting portfolio...", Toast.LENGTH_LONG).show()
                    }
                }
            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(recyclePortfolio)

            val touchHelperCallback =
                object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.DOWN) {
                    override fun onMove(recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val myUpdate = myUpdateViewHolder(viewHolder)
                        updatePortfolio = myUpdate.toString()
                     //   viewHolder.itemId

                        Log.d("ID", "$updatePortfolio")
                        val intent = Intent(this@MainActivity, UpdateActivity::class.java).apply {
                            putExtra(ITEM, itemPortfolio.text.toString())
                            putExtra(COST, costPortfolio.text.toString())
                            putExtra(TIME, timePortfolio.text.toString())
                            putExtra(DATE, datePortfolio.text.toString())
                        }
                        startActivity(intent)
                        Toast.makeText(this@MainActivity, "Edit product...", Toast.LENGTH_LONG).show()
                    }
                }
        val touchHelper = ItemTouchHelper(touchHelperCallback)
        touchHelper.attachToRecyclerView(recyclePortfolio)
           }


    override fun onRowLongClicked(position: Int) {
        enableActionMode(position)
    }
    private fun enableActionMode(position: Int): Int{
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback!!)
        }
        Log.d("position", "$position")
        positionInt = position.toString()
        return position
    }


    private inner class ActionModeCallback : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            mode.menuInflater.inflate(R.menu.action_mode, menu)
            return true
        }
        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                id.delete -> {
                    val position = positionInt.toInt()
                    val myPortfolio = mAdapter!!.getPortfolioAtPosition(position)
                    portfolioViewModel.deletePortfolio(myPortfolio)
                    Toast.makeText(this@MainActivity, "Deleting", Toast.LENGTH_LONG).show()
                    mode.finish()
                    true
                }
                id.edit -> {
                    val intent = Intent(this@MainActivity, UpdateActivity::class.java).apply {
                        putExtra(ITEM, itemPortfolio.text.toString())
                        putExtra(COST, costPortfolio.text.toString())
                        putExtra(TIME, timePortfolio.text.toString())
                        putExtra(DATE, datePortfolio.text.toString())
                    }
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
        override fun onDestroyActionMode(mode: ActionMode) {
            actionMode = null
        }
    }


   private fun myDeleteViewHolder(viewHolder: RecyclerView.ViewHolder): Portfolio{
        val position = viewHolder.adapterPosition
        val myPortfolio = mAdapter!!.getPortfolioAtPosition(position)
        portfolioViewModel.deletePortfolio(myPortfolio)
       return myPortfolio
    }

    private fun myUpdateViewHolder(viewHolder: RecyclerView.ViewHolder): Int{
        val position = viewHolder.adapterPosition
        val myPortfolio =  mAdapter!!.getPortfolioAtPosition(position)
        return position
    }

    companion object{
        const val ITEM = "item"
        const val COST = "cost"
        const val TIME = "time"
        const val DATE = "date"
    }
}
