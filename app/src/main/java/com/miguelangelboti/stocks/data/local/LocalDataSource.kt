package com.miguelangelboti.stocks.data.local

import android.content.Context
import com.miguelangelboti.stocks.data.local.entities.toDomain
import com.miguelangelboti.stocks.data.local.entities.toEntity
import com.miguelangelboti.stocks.entities.Order
import com.miguelangelboti.stocks.entities.Stock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSource(context: Context) {

    // TODO: Pass the DAO in constructor instead of context to build ths database.
    private val dataBase = DataBase.getDatabase(context)
    private var stockDao = dataBase.stockDao()
    private var orderDao = dataBase.orderDao()

    suspend fun getStock(id: Int): Stock? {
        return stockDao.getStock(id)?.toDomain()
    }

    suspend fun getStock(symbol: String): Stock? {
        return stockDao.getStock(symbol)?.toDomain()
    }

    suspend fun addStock(stock: Stock): Int {
        return stockDao.insert(stock.toEntity()).toInt()
    }

    suspend fun getOrders(): List<Order> {
        return orderDao.getOrders().map { order ->
            val stock = getStock(order.stockId)
            require(stock != null) { "The stock was not found getting the orders!" }
            toDomain(order, stock)
        }
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
