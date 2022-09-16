package ru.kolyagin.allstock.domain.model

data class SymbolDomainModel(
    val description: String,
    val displaySymbol: String,
    val symbol: String,
    val isSaved: Boolean,
)