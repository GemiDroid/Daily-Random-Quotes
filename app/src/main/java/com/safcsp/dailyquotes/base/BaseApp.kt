package com.safcsp.dailyquotes.base

import android.app.Application
import android.content.Context
import com.safcsp.dailyquotes.data.datasource.local.AppDatabase
import com.safcsp.dailyquotes.di.randomQuoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(this)
    }

    override fun getApplicationContext(): Context {
        return super.getApplicationContext()
    }

    private fun initKoin(application: Application) {
        startKoin {
            androidContext(application)
            modules(randomQuoteModule)
        }
    }
}
