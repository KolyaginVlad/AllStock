package ru.kolyagin.allstock.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.kolyagin.allstock.domain.model.SymbolDomainModel

class SymbolPagingSourceFactory(
    private val list: List<SymbolDomainModel>,
    private val loadSize: Int
) {

    fun create(): Flow<PagingData<SymbolDomainModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = loadSize,
                enablePlaceholders = true,
                prefetchDistance = 1,
                initialLoadSize = loadSize,
                maxSize = 20
            ),
            pagingSourceFactory = { SymbolPagingSource(list) }
        ).flow
    }
}