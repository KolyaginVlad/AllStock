package ru.kolyagin.allstock.domain.mapper

import ru.kolyagin.allstock.common.Mapper
import ru.kolyagin.allstock.domain.model.SymbolDomainModel
import ru.kolyagin.allstock.presentation.model.SymbolInfo

interface SymbolsDomainModelMapper : Mapper<SymbolDomainModel, SymbolInfo>