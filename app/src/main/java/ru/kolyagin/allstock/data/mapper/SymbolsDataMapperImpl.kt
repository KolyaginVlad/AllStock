package ru.kolyagin.allstock.data.mapper

import ru.kolyagin.allstock.data.model.SymbolsData
import ru.kolyagin.allstock.domain.model.SymbolDomainModel

class SymbolsDataMapperImpl : SymbolsDataMapper {
    override fun mapTo(from: SymbolsData): SymbolDomainModel =
        SymbolDomainModel(
            from.description,
            from.displaySymbol,
            from.symbol
        )

    override fun mapFrom(from: SymbolDomainModel): SymbolsData =
        SymbolsData(
            "",
            from.description,
            from.displaySymbol,
            "",
            "",
            from.symbol,
            ""
        )
}