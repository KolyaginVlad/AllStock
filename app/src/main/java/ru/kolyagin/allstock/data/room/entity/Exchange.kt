package ru.kolyagin.allstock.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exchange(
    @PrimaryKey
    val code: String,
    val name: String
)