package ru.kolyagin.allstock.domain.mapper

import ru.kolyagin.allstock.domain.model.SymbolDomainModel
import ru.kolyagin.allstock.presentation.model.SymbolInfo

class SymbolsDomainModelMapperImpl : SymbolsDomainModelMapper {
    override fun mapTo(from: SymbolDomainModel): SymbolInfo =
        SymbolInfo(
            from.description,
            from.displaySymbol,
            from.symbol,
            null
        )

    override fun mapFrom(from: SymbolInfo): SymbolDomainModel =
        SymbolDomainModel(
            from.description,
            from.displaySymbol,
            from.symbol
        )
}