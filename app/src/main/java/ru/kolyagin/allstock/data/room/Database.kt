package ru.kolyagin.allstock.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kolyagin.allstock.data.room.dao.ExchangeDao
import ru.kolyagin.allstock.data.room.dao.SymbolDao
import ru.kolyagin.allstock.data.room.entity.Exchange
import ru.kolyagin.allstock.data.room.entity.ExchangeAndSymbols
import ru.kolyagin.allstock.data.room.entity.SymbolEntity


@Database(entities = [SymbolEntity::class, Exchange::class, ExchangeAndSymbols::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun symbolDao(): SymbolDao
    abstract fun exchangeDao(): ExchangeDao

}