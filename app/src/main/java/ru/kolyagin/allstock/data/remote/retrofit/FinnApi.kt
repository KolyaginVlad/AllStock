package ru.kolyagin.allstock.data.remote.retrofit

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kolyagin.allstock.data.model.StockPrice
import ru.kolyagin.allstock.data.model.SymbolsData

interface FinnApi {

    @GET("stock/symbol")
    fun getSymbols(@Query("exchange") exchange: String = "US"): Deferred<Response<List<SymbolsData>>>

    @GET("quote")
    fun getQuote(@Query("symbol") symbol: String): Deferred<Response<StockPrice>>
}