package com.miguelangelboti.stocks.data.local

import android.content.Context
import com.miguelangelboti.stocks.data.local.entities.toDomain
import com.miguelangelboti.stocks.data.local.entities.toEntity
import com.miguelangelboti.stocks.entities.Order

class LocalDataSource(context: Context) {

    // TODO: Pass the DAO in constructor instead of context to build ths database.
    private val dataBase = DataBase.getDatabase(context)
    private var stockDao = dataBase.stockDao()
    private var orderDao = dataBase.orderDao()

    suspend fun getOrders(): List<Order> {
        return orderDao.getOrders().map(::toDomain)
    }

    suspend fun addOrder(order: Order) {
        orderDao.insert(order.toEntity())
    }

    suspend fun deleteOrder(orderId: Int) {
        orderDao.delete(orderId)
    }

    suspend fun updatePrice(symbol: String, price: Float, priceDate: String) {
        stockDao.update(symbol, price, priceDate)
    }
}
