package com.example.myportfolio.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.myportfolio.R
import com.example.myportfolio.model.Portfolio
import com.example.myportfolio.view.MainActivity.Companion.COST
import com.example.myportfolio.view.MainActivity.Companion.DATE
import com.example.myportfolio.view.MainActivity.Companion.ITEM
import com.example.myportfolio.view.MainActivity.Companion.TIME
import com.example.myportfolio.viewmodel.PortfolioViewModel
import kotlinx.android.synthetic.main.activity_update.*

class UpdateActivity : AppCompatActivity(),  PortfolioAdapter.PortfolioAdapterListener {
    private lateinit var portfolioViewModel: PortfolioViewModel
    private var mAdapter: PortfolioAdapter? = null
    private lateinit var portfolioList: List<Portfolio>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        mAdapter = PortfolioAdapter(this, this)

        val itemReceived = intent.getStringExtra(ITEM)
        itemPort_update.setText(itemReceived)
        val costReceived = intent.getStringExtra(COST)
        costPort_update.setText(costReceived)
        val dateReceived = intent.getStringExtra(DATE)
        datePortfolio_update.setText(dateReceived)
        val timeReceived = intent.getStringExtra(TIME)
        timePort_update.text = timeReceived




        portfolioViewModel = ViewModelProvider(this).get(PortfolioViewModel::class.java)
        portfolioViewModel.allPortfolios.observe(this, Observer { portfolios ->
            Log.d("key", "print")

            for (portfolio in portfolios){
                Log.d("PORTFOLIO","${portfolio.portfolioId}")
            }
            portfolioList = portfolios

        })


        portfolio_addUpdate.setOnClickListener {
            if (TextUtils.isEmpty(itemPort_update.text) || TextUtils.isEmpty(costPort_update.text) || TextUtils.isEmpty(
                    timePort_update.text
                ) || TextUtils.isEmpty(datePortfolio_update.text)
            ) {
                Toast.makeText(this, "Portfolio field cant be empty", Toast.LENGTH_LONG).show()
            } else {
                val updatePortfolio = Intent(this, MainActivity::class.java)

                val update = Portfolio(itemPort_update.text.toString(), costPort_update.text.toString(), timePort_update.text.toString(), datePortfolio_update.text.toString())
                portfolioViewModel.updatePortfolioSingle(update)
                Log.d("suspend", "$update")
                startActivity(updatePortfolio)
            }
            finish()
        }


    }

    private fun getPortfolioPosition(position: Int): Portfolio{
        return portfolioList[position]
    }


    override fun onRowLongClicked(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
