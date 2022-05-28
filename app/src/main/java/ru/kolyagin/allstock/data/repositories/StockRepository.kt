package ru.kolyagin.allstock.data.repositories

import kotlinx.coroutines.flow.Flow
import ru.kolyagin.allstock.domain.model.StockDomainModel
import ru.kolyagin.allstock.domain.model.SymbolDomainModel

interface StockRepository {
    suspend fun getListOfSymbols(): Result<List<SymbolDomainModel>>

    fun openWebSocket(
        subscribeFlow: Flow<List<String>>,
        unSubscribeFlow: Flow<List<String>>,
        onError: () -> Unit,
        onPriceUpdated: (List<StockDomainModel>) -> Unit
    )

    suspend fun getStockPrice(symbol: String): Result<Double>

    fun closeWebSocket()
}