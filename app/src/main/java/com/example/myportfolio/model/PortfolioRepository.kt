package com.example.myportfolio.model

import androidx.lifecycle.LiveData
import java.sql.RowId

class PortfolioRepository(private val portfolioDao: PortfolioDao) {
    val allPortfolio: LiveData<List<Portfolio>> = portfolioDao.getAllPortfolio()

  suspend  fun insertPortfolio(portfolio: Portfolio){
        portfolioDao.insertPortfolio(portfolio)
    }

    fun deletePortfolio(portfolio: Portfolio){
        portfolioDao.deletePortfolio(portfolio)
    }

    suspend fun deleteAll(){
        portfolioDao.deleteAll()
    }
   /* fun updatePortfolio(portfolio: Portfolio){
        portfolioDao.updatePortfolio(portfolio, portfolioId)
    }*/
     fun updatePortfolioSingle(portfolio: Portfolio){
        portfolioDao.updatePortfolioSingle(portfolio)
    }
}