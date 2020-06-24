package com.miguelangelboti.stocks.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.miguelangelboti.stocks.data.local.entities.OrderEntity

@Dao
interface OrderDao {

    @Query("SELECT * from orders")
    suspend fun getOrders(): List<OrderEntity>

    @Insert
    suspend fun insert(order: OrderEntity)

    @Query("DELETE FROM orders WHERE id = :orderId")
    suspend fun delete(orderId: Int)
}
