package com.miguelangelboti.stocks.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.miguelangelboti.stocks.data.local.entities.StockEntity

@Dao
interface StockDao {

    @Query("SELECT * from stocks")
    suspend fun getStocks(): List<StockEntity>

    @Query("SELECT * from stocks WHERE id = :id")
    suspend fun getStock(id: Int): StockEntity?

    @Query("SELECT * from stocks WHERE symbol = :symbol")
    suspend fun getStock(symbol: String): StockEntity?

    @Insert
    suspend fun insert(entity: StockEntity): Long

    @Query("UPDATE stocks SET price = :price, price_open = :priceOpen, price_date = :priceDate WHERE symbol = :symbol")
    suspend fun update(symbol: String, price: Float, priceOpen: Float, priceDate: String)
}
