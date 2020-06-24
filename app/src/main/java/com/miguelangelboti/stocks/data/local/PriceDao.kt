package com.miguelangelboti.stocks.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.miguelangelboti.stocks.data.local.entities.PriceEntity

@Dao
interface PriceDao {

//    @Query("SELECT * from prices")
//    suspend fun getPrices(): List<PriceEntity>

    @Insert
    suspend fun insert(order: PriceEntity)

    @Query("DELETE FROM prices")
    suspend fun clear()
}
