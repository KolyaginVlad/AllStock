package ru.kolyagin.allstock.data.mapper

import ru.kolyagin.allstock.data.model.Stock
import ru.kolyagin.allstock.domain.model.StockDomainModel

class StockMapperImpl : StockMapper {
    override fun mapTo(from: Stock): StockDomainModel =
        StockDomainModel(
            from.p,
            from.s
        )

    override fun mapFrom(from: StockDomainModel): Stock =
        Stock(
            from.price,
            from.symbol,
            0,
            0.0
        )
}