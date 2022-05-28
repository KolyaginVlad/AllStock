package ru.kolyagin.allstock.data.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kolyagin.allstock.Constants
import ru.kolyagin.allstock.Constants.BASE_URL
import ru.kolyagin.allstock.data.mapper.StockMapper
import ru.kolyagin.allstock.data.mapper.StockMapperImpl
import ru.kolyagin.allstock.data.mapper.SymbolsDataMapper
import ru.kolyagin.allstock.data.mapper.SymbolsDataMapperImpl
import ru.kolyagin.allstock.data.remote.retrofit.FinnApi
import ru.kolyagin.allstock.data.repositories.StockRepository
import ru.kolyagin.allstock.data.repositories.StockRepositoryImpl

val dataModule = module {
    single<StockRepository> { StockRepositoryImpl(get(), get()) }

    single<FinnApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(get())
            .build().create(FinnApi::class.java)
    }

    single {
        OkHttpClient.Builder().addInterceptor {
            val originalRequest = it.request()
            val urlOriginal = originalRequest.url

            val url = urlOriginal.newBuilder()
                .addQueryParameter("token", Constants.TOKEN)
                .build()

            val newRequest = originalRequest.newBuilder().url(url)
            it.proceed(newRequest.build())
        }.build()
    }

    single<SymbolsDataMapper> { SymbolsDataMapperImpl() }

    single<StockMapper> { StockMapperImpl() }
}