package ru.kolyagin.allstock.data.remote.websocket

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.kolyagin.allstock.Constants
import ru.kolyagin.allstock.common.toStock
import ru.kolyagin.allstock.data.mapper.StockMapper
import ru.kolyagin.allstock.domain.model.StockDomainModel

class StockWebSocket(
    private val onPriceUpdated: (List<StockDomainModel>) -> Unit,
    private val onError: () -> Unit,
    private val subscribeFlow: Flow<List<String>>,
    private val unSubscribeFlow: Flow<List<String>>,
) : WebSocketListener(), KoinComponent {

    private val stockMapper: StockMapper by inject()

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        t.printStackTrace()
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        CoroutineScope(Dispatchers.IO).launch {
            if (text == Constants.PING)
                return@launch
            val list =
                text.toStock()?.map { stockMapper.mapTo(it) } ?: run { onError(); emptyList() }
            onPriceUpdated(list)
        }
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        //Test
//        webSocket.send("{\"type\":\"subscribe\",\"symbol\":\"BINANCE:BTCUSDT\"}")
//        webSocket.send("{\"type\":\"subscribe\",\"symbol\":\"AAPL\"}")
//        webSocket.send("{\"type\":\"subscribe\",\"symbol\":\"AMZN\"}")
//        webSocket.send("{\"type\":\"subscribe\",\"symbol\":\"MSFT\"}")

        CoroutineScope(Dispatchers.IO).launch {
            subscribeFlow.onEach { list ->
                list.forEach {
                    webSocket.send("{\"type\":\"subscribe\",\"symbol\":\"$it\"}")
                }
            }.launchIn(this)
            unSubscribeFlow.onEach { list ->
                list.forEach {
                    webSocket.send("{\"type\":\"unsubscribe\",\"symbol\":\"$it\"}")
                }
            }.launchIn(this)
        }
    }

}