package ru.kolyagin.allstock.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kolyagin.allstock.presentation.MainViewModel

val presentationModule = module {
    viewModel { MainViewModel(get()) }
}