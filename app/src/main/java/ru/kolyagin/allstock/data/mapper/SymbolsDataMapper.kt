package ru.kolyagin.allstock.data.mapper

import ru.kolyagin.allstock.common.Mapper
import ru.kolyagin.allstock.data.model.SymbolsData
import ru.kolyagin.allstock.domain.model.SymbolDomainModel

interface SymbolsDataMapper : Mapper<SymbolsData, SymbolDomainModel>