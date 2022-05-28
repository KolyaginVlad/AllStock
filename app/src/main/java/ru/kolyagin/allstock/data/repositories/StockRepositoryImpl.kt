package ru.kolyagin.allstock.data.repositories

import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import ru.kolyagin.allstock.Constants
import ru.kolyagin.allstock.common.safeAwait
import ru.kolyagin.allstock.data.mapper.SymbolsDataMapper
import ru.kolyagin.allstock.data.remote.retrofit.FinnApi
import ru.kolyagin.allstock.data.remote.websocket.StockWebSocket
import ru.kolyagin.allstock.domain.model.StockDomainModel
import ru.kolyagin.allstock.domain.model.SymbolDomainModel

class StockRepositoryImpl(
    private val finnApi: FinnApi,
    private val mapper: SymbolsDataMapper
) : StockRepository {

    private var webSocket: WebSocket? = null

    override suspend fun getStockPrice(symbol: String): Result<Double> {
        val stockPrice = finnApi.getQuote(symbol).safeAwait()?.body()?.c
            ?: return Result.failure(Exception())
        return Result.success(stockPrice)
    }

    override suspend fun getListOfSymbols(): Result<List<SymbolDomainModel>> {
        val list = finnApi.getSymbols().safeAwait()?.body()?.map {
            mapper.mapTo(it)
        } ?: return Result.failure(Exception())
        return Result.success(list)
    }

    override fun openWebSocket(
        subscribeFlow: Flow<List<String>>,
        unSubscribeFlow: Flow<List<String>>,
        onError: () -> Unit,
        onPriceUpdated: (List<StockDomainModel>) -> Unit
    ) {
        val request = Request.Builder()
            .url(Constants.WEB_SOCKET_URL)
            .build()
        val listener = StockWebSocket(onPriceUpdated, onError, subscribeFlow, unSubscribeFlow)
        webSocket = OkHttpClient().newWebSocket(request, listener)
    }

    override fun closeWebSocket() {
        webSocket?.cancel()
    }
}