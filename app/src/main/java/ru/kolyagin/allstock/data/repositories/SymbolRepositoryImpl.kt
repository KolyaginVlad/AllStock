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
import ru.kolyagin.allstock.data.room.dao.SymbolDao
import ru.kolyagin.allstock.domain.mapper.SymbolsDomainModelMapper
import ru.kolyagin.allstock.domain.model.SymbolDomainModel

class SymbolRepositoryImpl(
    private val symbolDao: SymbolDao,
    private val mapper: SymbolEntityMapper,
) : SymbolRepository {
    override suspend fun saveSymbolsData(list: List<SymbolDomainModel>) {
        symbolDao.insertAll(list.map { mapper.mapFrom(it) })
    }

    override suspend fun getSymbolsPagingData(
        filter: String,
        onGet: (PagingData<SymbolDomainModel>) -> Unit
    ) = withContext(Dispatchers.IO) {
        SymbolPagingSourceFactory(symbolDao, Constants.LOAD_SIZE, filter).create().cachedIn(this)
            .onEach { pagingData ->
                onGet(pagingData.map { mapper.mapTo(it) })
            }.cachedIn(this).launchIn(this)
    }

    override suspend fun removeAll() {
        symbolDao.clearAll()
    }
}