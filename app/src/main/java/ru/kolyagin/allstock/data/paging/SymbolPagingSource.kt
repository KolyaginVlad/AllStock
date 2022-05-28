package ru.kolyagin.allstock.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.kolyagin.allstock.domain.model.SymbolDomainModel

class SymbolPagingSource(
    private val list: List<SymbolDomainModel>
) : PagingSource<Int, SymbolDomainModel>() {

    override fun getRefreshKey(state: PagingState<Int, SymbolDomainModel>): Int? {
        return state.anchorPosition
    }

    override val keyReuseSupported: Boolean
        get() = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SymbolDomainModel> {
        val key = params.key ?: 0
        val newList = list.subList(key, key + params.loadSize)
        val prevKey = if (params.key == null || key - params.loadSize < 0) {
            null
        } else {
            key - params.loadSize
        }
        return LoadResult.Page(newList, prevKey, key + params.loadSize)
    }
}