package ru.kolyagin.allstock.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.kolyagin.allstock.data.repositories.SymbolRepository
import ru.kolyagin.allstock.data.room.dao.SymbolDao
import ru.kolyagin.allstock.data.room.entity.SymbolEntity
import ru.kolyagin.allstock.domain.model.SymbolDomainModel

class SymbolPagingSourceFactory(
    private val symbolDao: SymbolDao,
    private val loadSize: Int,
    private val exchange: String,
    private val filter: String,
) {

    fun create(): Flow<PagingData<SymbolEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = loadSize,
                enablePlaceholders = true,
                prefetchDistance = 1,
                initialLoadSize = loadSize,
                maxSize = 20
            ),
            pagingSourceFactory = {
                symbolDao.pagingSource(filter, exchange)
            }
        ).flow
    }
}