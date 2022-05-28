package ru.kolyagin.allstock

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.kolyagin.allstock.data.di.dataModule
import ru.kolyagin.allstock.domain.di.domainModule
import ru.kolyagin.allstock.presentation.di.presentationModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(arrayListOf(presentationModule, domainModule, dataModule))
        }
    }
}