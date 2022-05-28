package ru.kolyagin.allstock.domain

import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import ru.kolyagin.allstock.Constants
import ru.kolyagin.allstock.data.paging.SymbolPagingSourceFactory
import ru.kolyagin.allstock.data.repositories.StockRepository
import ru.kolyagin.allstock.domain.mapper.StockDomainModelMapper
import ru.kolyagin.allstock.domain.mapper.SymbolsDomainModelMapper
import ru.kolyagin.allstock.domain.model.SymbolDomainModel
import ru.kolyagin.allstock.presentation.model.StockInfo
import ru.kolyagin.allstock.presentation.model.SymbolInfo

class MainInteractorImpl(
    private val stockRepository: StockRepository,
    private val symbolsMapper: SymbolsDomainModelMapper,
    private val stockMapper: StockDomainModelMapper,
) : MainInteractor {
    private var out: MainInteractorOut? = null

    override fun setOut(out: MainInteractorOut) {
        this.out = out
    }

    override suspend fun loadData(): Unit = withContext(Dispatchers.IO) {
        val result = stockRepository.getListOfSymbols()
        setResult(result, this)
    }

    override fun openWebSocket(
        subscribeFlow: Flow<List<String>>,
        unSubscribeFlow: Flow<List<String>>,
    ) {
        stockRepository.openWebSocket(
            subscribeFlow,
            unSubscribeFlow,
            { out?.showError() },
        ) { stocks ->
            out?.updatePrice(stocks.map { stockMapper.mapTo(it) })
        }
    }

    override fun closeWebSocket() {
        stockRepository.closeWebSocket()
    }

    override suspend fun loadInitialPrices(items: List<SymbolInfo>) {
        val list = mutableListOf<StockInfo>()
        items.forEachIndexed { i, s ->
            stockRepository.getStockPrice(s.symbol).onSuccess {
                list.add(StockInfo(it, s.symbol))
                if (i == items.size - 1) {
                    out?.updatePrice(list)
                }
            }.onFailure {
                list.add(StockInfo(0.0, s.symbol))
                if (i == items.size - 1) {
                    out?.updatePrice(list)
                }
            }
        }
    }

    private fun setResult(
        result: Result<List<SymbolDomainModel>>,
        scope: CoroutineScope
    ) {
        result.fold(
            onSuccess = { list ->
                SymbolPagingSourceFactory(list, Constants.LOAD_SIZE).create().cachedIn(scope)
                    .onEach { pagingData ->
                        out?.setPagingDataOfSymbols(pagingData.map { symbolsMapper.mapTo(it) })
                    }.cachedIn(scope).launchIn(scope)
            },
            onFailure = {
                out?.showError()
            }
        )
    }
}