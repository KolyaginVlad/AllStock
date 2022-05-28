package ru.kolyagin.allstock.domain.mapper

import ru.kolyagin.allstock.domain.model.StockDomainModel
import ru.kolyagin.allstock.presentation.model.StockInfo

class StockDomainModelMapperImpl : StockDomainModelMapper {
    override fun mapTo(from: StockDomainModel): StockInfo =
        StockInfo(
            from.price,
            from.symbol
        )

    override fun mapFrom(from: StockInfo): StockDomainModel =
        StockDomainModel(
            from.price,
            from.symbol
        )
}