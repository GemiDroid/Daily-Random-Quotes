package com.safcsp.dailyquotes.data.repository

import com.safcsp.dailyquotes.data.entities.Quote
import io.reactivex.rxjava3.core.Single

interface IRandomQuotesRepository {
    fun getRandomQuote(): Single<Quote>
}
