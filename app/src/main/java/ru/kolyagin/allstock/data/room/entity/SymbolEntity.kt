package ru.kolyagin.allstock.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Symbol")
data class SymbolEntity(
    val description: String,
    val displaySymbol: String,
    @PrimaryKey
    val symbol: String,
    val isSaved: Boolean,
)