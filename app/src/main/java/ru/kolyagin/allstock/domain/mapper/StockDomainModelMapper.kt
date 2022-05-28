package ru.kolyagin.allstock.domain.mapper

import ru.kolyagin.allstock.common.Mapper
import ru.kolyagin.allstock.domain.model.StockDomainModel
import ru.kolyagin.allstock.presentation.model.StockInfo

interface StockDomainModelMapper : Mapper<StockDomainModel, StockInfo>