package com.miguelangelboti.stocks.data.local

import android.content.Context
import com.miguelangelboti.stocks.data.local.entities.toDomain
import com.miguelangelboti.stocks.data.local.entities.toEntity
import com.miguelangelboti.stocks.entities.Order
import com.miguelangelboti.stocks.entities.Stock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class LocalDataSource(context: Context) {

    // TODO: Pass the DAO in constructor instead of context to build ths database.
    private val dataBase = DataBase.getDatabase(context)
    private var stockDao = dataBase.stockDao()
    private var orderDao = dataBase.orderDao()

    suspend fun getStocks(): List<Stock> {
        Timber.d("getStocks()")
        return stockDao.getStocks().map { it.toDomain() }
    }

    suspend fun getStock(id: Int): Stock? {
        Timber.d("getStock($id)")
        return stockDao.getStock(id)?.toDomain()
    }

    suspend fun getStock(symbol: String): Stock? {
        Timber.d("getStock($symbol)")
        return stockDao.getStock(symbol)?.toDomain()
    }

    suspend fun addStock(stock: Stock): Int {
        Timber.d("addStock($stock)")
        return stockDao.insert(stock.toEntity()).toInt()
    }

    suspend fun getOrders(): List<Order> {
        Timber.d("getOrders()")
        return orderDao.getOrders().map { order ->
            val stock = getStock(order.stockId)
            require(stock != null) { "The stock was not found getting the orders!" }
            toDomain(order, stock)
        }
    }

    suspend fun addOrder(order: Order) {
        Timber.d("addOrder($order)")
        orderDao.insert(order.toEntity())
    }

    suspend fun deleteOrder(orderId: Int) {
        Timber.d("deleteOrder($orderId)")
        orderDao.delete(orderId)
    }

    suspend fun updatePrice(symbol: String, price: Float, priceDate: String) {
        Timber.d("updatePrice($symbol, $price, $priceDate)")
        stockDao.update(symbol, price, priceDate)
    }
}
