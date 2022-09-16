package ru.kolyagin.allstock.data.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.kolyagin.allstock.data.room.entity.SymbolEntity

@Dao
interface SymbolDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(users: List<SymbolEntity>)

    @Query("SELECT * FROM Symbol WHERE displaySymbol LIKE '%' || :query || '%' ORDER BY isSaved DESC")
    fun pagingSource(query: String): PagingSource<Int, SymbolEntity>

    @Query("DELETE FROM Symbol")
    suspend fun clearAll()

    @Query("Update Symbol set isSaved=:checked where symbol=:symbol")
    suspend fun updateSymbol(symbol: String, checked: Boolean)
}