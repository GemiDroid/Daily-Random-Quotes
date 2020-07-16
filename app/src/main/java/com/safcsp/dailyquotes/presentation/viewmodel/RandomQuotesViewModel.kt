package com.safcsp.dailyquotes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.safcsp.dailyquotes.domain.RandomQuotesUseCase

class RandomQuotesViewModel(private val randomQuotesUseCase: RandomQuotesUseCase) : ViewModel() {


    init {
        loadData()
    }

    fun loadData() {
        randomQuotesUseCase.execute(null)
    }

    val getRandomQuote = randomQuotesUseCase.getQuote

    val getProgress = randomQuotesUseCase.getProgress

    val getError = randomQuotesUseCase.getError

    override fun onCleared() {
        super.onCleared()
        randomQuotesUseCase.cleanUnUsedResources()
    }
}