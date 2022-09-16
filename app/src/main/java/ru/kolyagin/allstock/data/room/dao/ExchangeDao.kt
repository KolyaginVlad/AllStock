package ru.kolyagin.allstock.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import ru.kolyagin.allstock.data.room.entity.Exchange
import ru.kolyagin.allstock.data.room.entity.ExchangeAndSymbols

@Dao
interface ExchangeDao {

    @Insert(onConflict = IGNORE)
    fun setNewSymbols(value: ExchangeAndSymbols)

    @Query("Select * from Exchange")
    fun getExchanges(): List<Exchange>
}