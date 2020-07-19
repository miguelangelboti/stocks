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
        return stockDao.getStocks().map {
            val orders = orderDao.getOrders(it.id).toDomain()
            it.toDomain(orders)
        }
    }

    fun getStocksFlow(): Flow<List<Stock>> {
        Timber.d("getStocks()")
        return stockDao.getStocksFlow().map {
            it.map { stock ->
                val orders = orderDao.getOrders(stock.id).toDomain()
                stock.toDomain(orders)
            }
        }
    }

    suspend fun getStock(id: Int): Stock? {
        Timber.d("getStock($id)")
        val stock = stockDao.getStock(id) ?: return null
        val orders = orderDao.getOrders(stock.id).toDomain()
        return stock.toDomain(orders)
    }

    suspend fun getStock(symbol: String): Stock? {
        Timber.d("getStock($symbol)")
        val stock = stockDao.getStock(symbol) ?: return null
        val orders = orderDao.getOrders(stock.id).toDomain()
        return stockDao.getStock(symbol)?.toDomain(orders)
    }

    suspend fun addStock(stock: Stock): Int {
        Timber.d("addStock($stock)")
        return stockDao.insert(stock.toEntity()).toInt()
    }

    suspend fun addOrder(stockId: Int, order: Order) {
        Timber.d("addOrder($order)")
        orderDao.insert(order.toEntity(stockId))
    }

    suspend fun deleteOrder(orderId: Int) {
        Timber.d("deleteOrder($orderId)")
        orderDao.delete(orderId)
    }

    suspend fun updateStocks(stocks: List<Stock>) {
        stocks.forEach {
            stockDao.update(it.symbol, it.price, it.priceOpen, it.priceDate)
        }
    }
}
