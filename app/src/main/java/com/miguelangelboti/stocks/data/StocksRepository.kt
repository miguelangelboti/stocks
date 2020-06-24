package com.miguelangelboti.stocks.data

import android.content.Context
import com.miguelangelboti.stocks.data.local.LocalDataSource
import com.miguelangelboti.stocks.data.network.NetworkDataSource
import com.miguelangelboti.stocks.entities.Order
import com.miguelangelboti.stocks.entities.Price
import com.miguelangelboti.stocks.entities.Stock
import com.miguelangelboti.stocks.utils.getUtcDatetime

class StocksRepository(context: Context) {

    private val localDataSource = LocalDataSource(context)
    private val networkDataSource = NetworkDataSource()

    private val cacheTime = 60
    private val lasTimestamp by lazy { System.currentTimeMillis() }

    suspend fun addOrder(order: Order) {
        localDataSource.addOrder(order)
    }

    suspend fun deleteOrder(orderId: Int) {
        localDataSource.deleteOrder(orderId)
    }

    suspend fun getOrders(): List<Order> {
        val elapsedTime = System.currentTimeMillis() - lasTimestamp
        if (elapsedTime > cacheTime) {
            return localDataSource.getOrders().updatePrices()
        }
        return localDataSource.getOrders()
    }

    private suspend fun List<Order>.updatePrices(): List<Order> {
        val prices = map { it.stock.symbol }
            .distinct()
            .associateWith { networkDataSource.getStockPrice(it) }
        val date = getUtcDatetime()
        return map {
            val price = prices[it.stock.symbol]
            it.copy(stock = it.stock.copy(price = Price(price!!, date)))
        }
    }

    suspend fun searchSymbol(symbol: String): List<Stock> {
        return networkDataSource.searchSymbol(symbol)
    }
}
