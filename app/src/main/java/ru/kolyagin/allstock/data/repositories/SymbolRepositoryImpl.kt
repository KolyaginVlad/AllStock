package ru.kolyagin.allstock.data.repositories

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import ru.kolyagin.allstock.Constants
import ru.kolyagin.allstock.data.mapper.SymbolEntityMapper
import ru.kolyagin.allstock.data.paging.SymbolPagingSourceFactory
import ru.kolyagin.allstock.data.room.dao.ExchangeDao
import ru.kolyagin.allstock.data.room.dao.SymbolDao
import ru.kolyagin.allstock.data.room.entity.Exchange
import ru.kolyagin.allstock.data.room.entity.ExchangeAndSymbols
import ru.kolyagin.allstock.domain.model.SymbolDomainModel

class SymbolRepositoryImpl(
    private val symbolDao: SymbolDao,
    private val exchangeDao: ExchangeDao,
    private val mapper: SymbolEntityMapper,
) : SymbolRepository {
    override suspend fun saveSymbolsData(list: List<SymbolDomainModel>, exchange: String) {
        val mappedList = list.map { mapper.mapFrom(it) }
        symbolDao.insertAll(mappedList)
        list.forEach {
            exchangeDao.setNewSymbols(ExchangeAndSymbols(it.symbol, exchange))
        }
    }

    override suspend fun getSymbolsPagingData(
        exchange: String,
        filter: String,
        onGet: (PagingData<SymbolDomainModel>) -> Unit
    ) = withContext(Dispatchers.IO) {
        SymbolPagingSourceFactory(symbolDao, Constants.LOAD_SIZE, filter, exchange).create().cachedIn(this)
            .onEach { pagingData ->
                onGet(pagingData.map { mapper.mapTo(it) })
            }.cachedIn(this).launchIn(this)
    }

    override suspend fun getExchanges(): List<Exchange> = withContext(Dispatchers.IO) {
        exchangeDao.getExchanges()
    }

    override suspend fun updateSymbol(symbol: String, checked: Boolean) {
        symbolDao.updateSymbol(symbol, checked)
    }
}