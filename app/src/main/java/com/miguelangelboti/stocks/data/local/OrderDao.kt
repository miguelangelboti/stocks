package com.miguelangelboti.stocks.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.miguelangelboti.stocks.data.local.entities.OrderEntity

@Dao
interface OrderDao {

    @Query("SELECT * from orders")
    suspend fun getOrders(): List<OrderEntity>

    @Insert
    suspend fun insert(entity: OrderEntity)

    @Query("DELETE FROM orders WHERE id = :id")
    suspend fun delete(id: Int)
}
