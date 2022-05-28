package ru.kolyagin.allstock.data.mapper

import ru.kolyagin.allstock.common.Mapper
import ru.kolyagin.allstock.data.model.Stock
import ru.kolyagin.allstock.domain.model.StockDomainModel

interface StockMapper : Mapper<Stock, StockDomainModel>