package ru.kolyagin.allstock.common

import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kolyagin.allstock.data.model.Stock
import ru.kolyagin.allstock.data.model.StockData

/**
 * Exception safe вариант await-а, который вернет null,
 * если произошла ошибка во время выполнения await
 */
suspend fun <T> Deferred<T>.safeAwait(): T? {
    try {
        return await()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

/**
 * Преобразование ответа от WebSocket
 */
suspend fun String.toStock(): List<Stock>? = withContext(Dispatchers.Default) {
    try {
        val stockList = Gson().fromJson(this@toStock, StockData::class.java)
        return@withContext stockList.data
    } catch (e: Exception) {
        return@withContext null
    }
}