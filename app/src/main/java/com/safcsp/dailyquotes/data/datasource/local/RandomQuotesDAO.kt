package com.safcsp.dailyquotes.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.safcsp.dailyquotes.data.entities.Quote
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single


@Dao
interface RandomQuotesDAO {
    @Insert
    fun saveQuotes(quote: Quote)

    @Query("SELECT * FROM quote ORDER BY Id DESC LIMIT 1")
    fun getLastQuote(): Quote
}