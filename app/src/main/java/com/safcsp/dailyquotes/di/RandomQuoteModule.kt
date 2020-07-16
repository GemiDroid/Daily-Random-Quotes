package com.safcsp.dailyquotes.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.safcsp.dailyquotes.base.Executors
import com.safcsp.dailyquotes.base.IExecutors
import com.safcsp.dailyquotes.data.BASE_URL
import com.safcsp.dailyquotes.data.CONNECT_TIMEOUT
import com.safcsp.dailyquotes.data.READ_TIMEOUT
import com.safcsp.dailyquotes.data.WRITE_TIMEOUT
import com.safcsp.dailyquotes.data.datasource.local.AppDatabase
import com.safcsp.dailyquotes.data.datasource.local.RandomQuotesDAO
import com.safcsp.dailyquotes.data.datasource.remote.RandomQuotesAPI
import com.safcsp.dailyquotes.data.repository.IRandomQuotesRepository
import com.safcsp.dailyquotes.data.repository.RandomQuotesRepository
import com.safcsp.dailyquotes.domain.RandomQuotesUseCase
import com.safcsp.dailyquotes.presentation.viewmodel.RandomQuotesViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val randomQuoteModule = module {

    single { Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "random_quote.db")
        .allowMainThreadQueries().build() }

    single { get<AppDatabase>().quoteDao() }

    single<OkHttpClient> {
        val builder = OkHttpClient.Builder()
        builder
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        builder.build()
    }

    single { GsonBuilder().serializeNulls().create() }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get<OkHttpClient>())
            .build()
    }

    factory { get<Retrofit>().create(RandomQuotesAPI::class.java) }

    factory<IRandomQuotesRepository> { RandomQuotesRepository(get()) }

    factory<IExecutors> { Executors() }

    factory { CompositeDisposable() }

    factory { RandomQuotesUseCase(get(), get(), get(), get() as RandomQuotesDAO) }

    viewModel { RandomQuotesViewModel(get()) }
}