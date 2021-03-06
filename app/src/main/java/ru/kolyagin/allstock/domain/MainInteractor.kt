package ru.kolyagin.allstock.domain

import kotlinx.coroutines.flow.Flow
import ru.kolyagin.allstock.presentation.model.SymbolInfo

interface MainInteractor {
    fun setOut(out: MainInteractorOut)

    suspend fun loadData()

    fun openWebSocket(
        subscribeFlow: Flow<List<String>>,
        unSubscribeFlow: Flow<List<String>>,
    )

    suspend fun loadInitialPrices(items: List<SymbolInfo>)

    fun closeWebSocket()
}