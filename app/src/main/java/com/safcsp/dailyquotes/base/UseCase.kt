package com.safcsp.dailyquotes.base

interface UseCase<in T, out S> {

    fun execute(value : T) : S
}
