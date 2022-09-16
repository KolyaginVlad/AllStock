package ru.kolyagin.allstock.data.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.Job
import ru.kolyagin.allstock.data.room.entity.Exchange
import ru.kolyagin.allstock.domain.model.SymbolDomainModel

interface SymbolRepository {
    suspend fun saveSymbolsData(list: List<SymbolDomainModel>, exchange: String)

    suspend fun updateSymbol(symbol: String, checked: Boolean)

    suspend fun getSymbolsPagingData(
        exchange: String,
        filter: String,
        onGet: (PagingData<SymbolDomainModel>) -> Unit
    ): Job

    suspend fun getExchanges(): List<Exchange>
}