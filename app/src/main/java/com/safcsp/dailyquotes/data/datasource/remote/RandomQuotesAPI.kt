package com.safcsp.dailyquotes.data.datasource.remote

import com.safcsp.dailyquotes.data.entities.Quote
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface RandomQuotesAPI {

    @GET("quotes/random")
    fun getRandomQuote() : Single<Quote>

}
