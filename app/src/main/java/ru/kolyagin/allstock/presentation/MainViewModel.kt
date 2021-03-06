package ru.kolyagin.allstock.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.kolyagin.allstock.domain.MainInteractor
import ru.kolyagin.allstock.domain.MainInteractorOut
import ru.kolyagin.allstock.presentation.events.Event
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

    init {
        interactor.setOut(this)
    }

    fun onCreate(
        subscribeFlow: Flow<List<String>>,
        unSubscribeFlow: Flow<List<String>>,
    ) = viewModelScope.launch {
        interactor.openWebSocket(subscribeFlow, unSubscribeFlow)
        interactor.loadData()
    }

    fun loadInitialPrices(items: List<SymbolInfo>) = viewModelScope.launch {
        interactor.loadInitialPrices(items)
    }

    fun onDestroy() {
        interactor.closeWebSocket()
    }

    override fun setPagingDataOfSymbols(list: PagingData<SymbolInfo>): Job = viewModelScope.launch {
        listOfSymbolsChannel.send(list)
    }

    override fun updatePrice(info: List<StockInfo>): Job = viewModelScope.launch {
        info.forEach {
            priceChannel.send(it)
        }
    }

    override fun showError(): Job = viewModelScope.launch {
        eventChannel.send(Event.ErrorEvent)
    }

}