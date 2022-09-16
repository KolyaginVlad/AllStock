package ru.kolyagin.allstock.domain

import androidx.paging.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.kolyagin.allstock.data.repositories.StockRepository
import ru.kolyagin.allstock.data.repositories.SymbolRepository
import ru.kolyagin.allstock.domain.mapper.StockDomainModelMapper
import ru.kolyagin.allstock.domain.mapper.SymbolsDomainModelMapper
import ru.kolyagin.allstock.domain.model.SymbolDomainModel
import ru.kolyagin.allstock.presentation.model.StockInfo
import ru.kolyagin.allstock.presentation.model.SymbolInfo

class MainInteractorImpl(
    private val stockRepository: StockRepository,
    private val symbolRepository: SymbolRepository,
    private val symbolsMapper: SymbolsDomainModelMapper,
    private val stockMapper: StockDomainModelMapper,
) : MainInteractor {
    private var out: MainInteractorOut? = null

    override fun setOut(out: MainInteractorOut) {
        this.out = out
    }

    override suspend fun loadData(): Unit = withContext(Dispatchers.IO) {
        val result = stockRepository.getListOfSymbols()
        setResult(result)
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

    override suspend fun setFilter(filter: String) {
        symbolRepository.getSymbolsPagingData(filter) { pagingData ->
            out?.setPagingDataOfSymbols(pagingData.map { symbolsMapper.mapTo(it) })
        }
    }

    override suspend fun onCheck(symbol: String, checked: Boolean) {
        symbolRepository.updateSymbol(symbol, checked)
    }

    private suspend fun setResult(
        result: Result<List<SymbolDomainModel>>,
    ) {
        result.fold(
            onSuccess = { list ->
                symbolRepository.saveSymbolsData(list)
                setFilter("")
            },
            onFailure = {
                out?.showError()
            }
        )
    }
}