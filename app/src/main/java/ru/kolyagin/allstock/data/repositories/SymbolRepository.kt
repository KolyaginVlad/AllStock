package ru.kolyagin.allstock.data.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.Job
import ru.kolyagin.allstock.domain.model.SymbolDomainModel

interface SymbolRepository {
    suspend fun saveSymbolsData(list: List<SymbolDomainModel>)

    suspend fun getSymbolsPagingData(
        filter: String,
        onGet: (PagingData<SymbolDomainModel>) -> Unit
    ): Job

    suspend fun removeAll()
}