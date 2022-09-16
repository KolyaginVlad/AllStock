package ru.kolyagin.allstock.domain

import androidx.paging.PagingData
import kotlinx.coroutines.Job
import ru.kolyagin.allstock.data.room.entity.Exchange
import ru.kolyagin.allstock.presentation.model.StockInfo
import ru.kolyagin.allstock.presentation.model.SymbolInfo


interface MainInteractorOut {

    fun setPagingDataOfSymbols(list: PagingData<SymbolInfo>): Job

    fun updatePrice(info: List<StockInfo>): Job

    fun showError(): Job

    fun showExchanges(exchanges: List<Exchange>)

}