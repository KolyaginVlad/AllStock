package ru.kolyagin.allstock.presentation.model

data class SymbolInfo(
    val description: String,
    val displaySymbol: String,
    val symbol: String,
    var price: Double?,
    val isSaved: Boolean
)