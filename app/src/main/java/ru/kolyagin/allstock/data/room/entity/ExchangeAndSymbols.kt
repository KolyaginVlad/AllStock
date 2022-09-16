package ru.kolyagin.allstock.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(primaryKeys = ["symbol", "code"],
    indices = [
        Index(value = ["symbol"]),
        Index(value = ["code"])
    ],
    foreignKeys = [
        ForeignKey(entity = SymbolEntity::class,
            parentColumns = ["symbol"],
            childColumns = ["symbol"]),
        ForeignKey(entity = Exchange::class,
            parentColumns = ["code"],
            childColumns = ["code"])
    ])
data class ExchangeAndSymbols(
    val symbol: String,
    val code: String
)