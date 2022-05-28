package ru.kolyagin.allstock

object Constants {
    const val TOKEN = "c8uv1qqad3iaocnjnn0g"
    const val BASE_URL = "https://finnhub.io/api/v1/"
    const val WEB_SOCKET_URL = "wss://ws.finnhub.io?token=$TOKEN"
    const val PING = "{\"type\":\"ping\"}"
    const val LOAD_SIZE = 5
}