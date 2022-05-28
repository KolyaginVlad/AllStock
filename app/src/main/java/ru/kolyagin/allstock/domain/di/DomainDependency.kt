package ru.kolyagin.allstock.domain.di

import org.koin.dsl.module
import ru.kolyagin.allstock.domain.MainInteractor
import ru.kolyagin.allstock.domain.MainInteractorImpl
import ru.kolyagin.allstock.domain.mapper.StockDomainModelMapper
import ru.kolyagin.allstock.domain.mapper.StockDomainModelMapperImpl
import ru.kolyagin.allstock.domain.mapper.SymbolsDomainModelMapper
import ru.kolyagin.allstock.domain.mapper.SymbolsDomainModelMapperImpl

val domainModule = module {
    factory<MainInteractor> { MainInteractorImpl(get(), get(), get()) }
    single<SymbolsDomainModelMapper> { SymbolsDomainModelMapperImpl() }
    single<StockDomainModelMapper> { StockDomainModelMapperImpl() }
}