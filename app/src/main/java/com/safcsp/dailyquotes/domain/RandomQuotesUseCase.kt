package com.safcsp.dailyquotes.domain

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.safcsp.dailyquotes.base.IExecutors
import com.safcsp.dailyquotes.base.UseCase
import com.safcsp.dailyquotes.data.datasource.local.RandomQuotesDAO
import com.safcsp.dailyquotes.data.entities.Quote
import com.safcsp.dailyquotes.data.repository.IRandomQuotesRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class RandomQuotesUseCase(
    private val executors: IExecutors,
    private val compositeDisposable: CompositeDisposable,
    private val repository: IRandomQuotesRepository,
    private val quotesDAO: RandomQuotesDAO

) : UseCase<Any?, Any> {

    override fun execute(value: Any?): Any {
        return compositeDisposable.add(
            repository.getRandomQuote()
                .subscribeOn(executors.getIOThread())
                .doOnSubscribe {
                    getProgress.postValue(true)
                }
                .doFinally {
                    getProgress.postValue(false)
                }
                .subscribe({ quote ->
                    if (quote != null) {
                        getQuote.postValue(quote)
                        quotesDAO.saveQuotes(quote)
                        getError.postValue(null)
                    }
                }, {
                    getError.postValue(it)
                   getQuote.postValue(quotesDAO.getLastQuote())
                })
        )
    }

    val getQuote = MutableLiveData<Quote>()
    val getError = MutableLiveData<Throwable>()
    val getProgress = MutableLiveData<Boolean>()


    fun cleanUnUsedResources() {
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }
}
