package com.miguelangelboti.stocks.data

import android.content.Context
import com.miguelangelboti.stocks.data.local.LocalDataSource
import com.miguelangelboti.stocks.data.network.NetworkDataSource
import com.miguelangelboti.stocks.entities.Order
import com.miguelangelboti.stocks.entities.Stock
import com.miguelangelboti.stocks.utils.getUtcDatetimeAsString

class StocksRepository(context: Context) {

    private val localDataSource = LocalDataSource(context)
    private val networkDataSource = NetworkDataSource()

    private val cacheTime = 60
    private var lasTimestamp = 0L

    suspend fun addOrder(order: Order) {
        val stock = getStock(order.stock.symbol)
        require(stock != null) { "The stock was not found adding a new order!" }
        localDataSource.addOrder(order.copy(stock = stock))
    }

    private suspend fun getStock(symbol: String): Stock? {
        return localDataSource.getStock(symbol)
            ?: networkDataSource.getStock(symbol)?.also {
                val id = localDataSource.addStock(it)
                return it.copy(id = id)
            }
    }

    suspend fun deleteOrder(orderId: Int) {
        localDataSource.deleteOrder(orderId)
    }

    suspend fun getOrders(): List<Order> {
        val elapsedTime = System.currentTimeMillis() - lasTimestamp
        if (elapsedTime > cacheTime) {
            lasTimestamp = System.currentTimeMillis()
            return localDataSource.getOrders().also { it.updatePrices() }
        }
        return localDataSource.getOrders()
    }

    private suspend fun List<Order>.updatePrices() {
        val date = getUtcDatetimeAsString()
        map { it.stock.symbol }
            .distinct()
            .forEach { symbol ->
                val price = networkDataSource.getStockPrice(symbol)
                localDataSource.updatePrice(symbol, price, date)
            }
    }

    suspend fun searchSymbol(symbol: String): List<Stock> {
        return networkDataSource.searchSymbol(symbol)
    }
}
