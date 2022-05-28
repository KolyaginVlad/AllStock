package ru.kolyagin.allstock.data.remote.websocket

interface SocketListener {
    fun onMessage(message: String)
}