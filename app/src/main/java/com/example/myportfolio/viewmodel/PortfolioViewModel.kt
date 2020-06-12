package com.example.myportfolio.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myportfolio.model.MyDatabase
import com.example.myportfolio.model.Portfolio
import com.example.myportfolio.model.PortfolioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.RowId

class PortfolioViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PortfolioRepository
    val allPortfolios: LiveData<List<Portfolio>>

    init {
        val portfolioDao = MyDatabase.getDatabase(
            application,
            viewModelScope
        ).portfolioDao()
        repository = PortfolioRepository(portfolioDao)
        allPortfolios = repository.allPortfolio
    }

    fun insertPortfolio(portfolio: Portfolio) = viewModelScope.launch(Dispatchers.IO){
        repository.insertPortfolio(portfolio)
    }

    fun deletePortfolio(portfolio: Portfolio) = viewModelScope.launch(Dispatchers.IO){
        repository.deletePortfolio(portfolio)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO){
        repository.deleteAll()
    }

  /*  fun updatePortfolio(portfolio: Portfolio, portfolioId: RowId){
        repository.updatePortfolio(portfolio, portfolioId)
    }*/
  fun updatePortfolioSingle(portfolio: Portfolio) = viewModelScope.launch(Dispatchers.IO){
        repository.updatePortfolioSingle(portfolio)
    }
}