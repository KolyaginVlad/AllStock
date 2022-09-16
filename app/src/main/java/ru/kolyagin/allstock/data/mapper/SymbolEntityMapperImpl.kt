package ru.kolyagin.allstock.data.mapper

import ru.kolyagin.allstock.data.room.entity.SymbolEntity
import ru.kolyagin.allstock.domain.model.SymbolDomainModel

class SymbolEntityMapperImpl : SymbolEntityMapper {
    override fun mapTo(from: SymbolEntity): SymbolDomainModel =
        SymbolDomainModel(
            description = from.description,
            displaySymbol = from.displaySymbol,
            symbol = from.symbol,
            isSaved = from.isSaved
        )

    override fun mapFrom(from: SymbolDomainModel): SymbolEntity =
        SymbolEntity(
            description = from.description,
            displaySymbol = from.displaySymbol,
            symbol = from.symbol,
            isSaved = from.isSaved
        )
}