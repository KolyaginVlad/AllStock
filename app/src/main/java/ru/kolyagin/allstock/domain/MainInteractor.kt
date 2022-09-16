package ru.kolyagin.allstock.domain

import kotlinx.coroutines.flow.Flow
import ru.kolyagin.allstock.presentation.model.SymbolInfo

interface MainInteractor {
    fun setOut(out: MainInteractorOut)

    suspend fun loadData(exchange: String)

    fun openWebSocket(
        subscribeFlow: Flow<List<String>>,
        unSubscribeFlow: Flow<List<String>>,
    )

    suspend fun loadInitialPrices(items: List<SymbolInfo>)

    fun closeWebSocket()

    suspend fun setFilter(filter: String, exchange: String)

    suspend fun onCheck(symbol: String, checked: Boolean)

    suspend fun getExchanges()
}