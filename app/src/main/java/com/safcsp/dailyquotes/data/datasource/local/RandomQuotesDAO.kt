package com.safcsp.dailyquotes.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.safcsp.dailyquotes.data.entities.Quote


@Dao
interface RandomQuotesDAO {
    @Insert
    fun saveQuotes(quote: Quote)

    @Query("SELECT * FROM quote ORDER BY Id DESC LIMIT 1")
    fun getLastQuote(): Quote
}