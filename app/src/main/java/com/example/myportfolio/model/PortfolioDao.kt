package com.example.myportfolio.model

import androidx.lifecycle.LiveData
import androidx.room.*
import java.sql.RowId

@Dao
interface PortfolioDao {
    @Query("SELECT * from portfolio ORDER BY portfolio_id DESC")
    fun getAllPortfolio(): LiveData<List<Portfolio>>

    @Insert
    suspend fun insertPortfolio(portfolio: Portfolio)

    @Delete
    fun deletePortfolio(portfolio: Portfolio)


  //  @Query("UPDATE PORTFOLIO SET portfolio_item=: WHERE id=:")
 /* @Query("UPDATE PORTFOLIO SET portfolio_id = :portfolioId")
    fun updatePortfolio(portfolio: Portfolio, portfolioId: RowId)*/

    @Query("DELETE FROM portfolio")
    suspend fun deleteAll()

    @Update
    fun updatePortfolioSingle(portfolio: Portfolio)
}