package ru.kolyagin.allstock.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.kolyagin.allstock.data.room.entity.Exchange
import ru.kolyagin.allstock.domain.MainInteractor
import ru.kolyagin.allstock.domain.MainInteractorOut
import ru.kolyagin.allstock.presentation.events.Event
import ru.kolyagin.allstock.presentation.events.EventIndicator
import ru.kolyagin.allstock.presentation.model.StockInfo
import ru.kolyagin.allstock.presentation.model.SymbolInfo

class MainViewModel(
    private val interactor: MainInteractor
) : ViewModel(), MainInteractorOut {

    private val listOfSymbolsChannel = Channel<PagingData<SymbolInfo>>(Channel.BUFFERED)
    val listOfSymbolsFlow = listOfSymbolsChannel.receiveAsFlow()

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    private val priceChannel = Channel<StockInfo>(Channel.BUFFERED)
    val priceFlow = priceChannel.receiveAsFlow()

    private val exchanges = Channel<Pair<List<Exchange>, String>>()
    val exchangesList = exchanges.receiveAsFlow()

    private val showIndicatorChannel = Channel<EventIndicator>()
    val showIndicator = showIndicatorChannel.receiveAsFlow()

    private var currentJob: Job? = null

    private var exchange = "US"

    init {
        interactor.setOut(this)
    }

    fun onCreate(
        subscribeFlow: Flow<List<String>>,
        unSubscribeFlow: Flow<List<String>>,
    ) = viewModelScope.launch {
        interactor.openWebSocket(subscribeFlow, unSubscribeFlow)
        currentJob?.cancel()
        currentJob = launch {
            interactor.loadData(exchange)
        }
    }

    fun loadInitialPrices(items: List<SymbolInfo>) = viewModelScope.launch {
        interactor.loadInitialPrices(items)
    }

    fun onDestroy() {
        interactor.closeWebSocket()
    }

    fun onCheck(data: SymbolInfo, isChecked: Boolean) {
        viewModelScope.launch {
            interactor.onCheck(data.symbol, isChecked)
        }
    }

    fun onFilterChanged(filter: String) = viewModelScope.launch {
        interactor.setFilter(filter, exchange)
    }

    fun onSelectExchange(code: String)  = viewModelScope.launch {
        showIndicatorChannel.send(EventIndicator.EventIndicatorShow)
        exchange = code
        currentJob?.cancel()
        currentJob = launch {
            interactor.loadData(exchange)
        }
    }

    fun getExchanges() = viewModelScope.launch {
        interactor.getExchanges()
    }

    override fun showExchanges(list: List<Exchange>) {
        viewModelScope.launch {
            exchanges.send(list to exchange)
        }
    }

    override fun setPagingDataOfSymbols(list: PagingData<SymbolInfo>) = viewModelScope.launch {
        listOfSymbolsChannel.send(list)
    }

    override fun updatePrice(info: List<StockInfo>) = viewModelScope.launch {
        info.forEach {
            priceChannel.send(it)
        }
    }

    override fun showError() = viewModelScope.launch {
        eventChannel.send(Event.ErrorEvent)
    }

}