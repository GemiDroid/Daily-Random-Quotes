package com.safcsp.dailyquotes.data.repository

import com.safcsp.dailyquotes.data.datasource.remote.RandomQuotesAPI
import com.safcsp.dailyquotes.data.entities.Quote
import io.reactivex.rxjava3.core.Single

class RandomQuotesRepository(private val randomQuotesAPI: RandomQuotesAPI)
    : IRandomQuotesRepository {

    override fun getRandomQuote(): Single<Quote> = randomQuotesAPI.getRandomQuote()
}
