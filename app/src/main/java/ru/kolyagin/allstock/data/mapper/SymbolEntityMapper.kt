package ru.kolyagin.allstock.data.mapper

import ru.kolyagin.allstock.common.Mapper
import ru.kolyagin.allstock.data.room.entity.SymbolEntity
import ru.kolyagin.allstock.domain.model.SymbolDomainModel

interface SymbolEntityMapper : Mapper<SymbolEntity, SymbolDomainModel>