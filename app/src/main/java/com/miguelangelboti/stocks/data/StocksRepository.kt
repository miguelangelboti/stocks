package com.miguelangelboti.stocks.data

import android.content.Context
import com.miguelangelboti.stocks.data.local.LocalDataSource
import com.miguelangelboti.stocks.data.network.NetworkDataSource
import com.miguelangelboti.stocks.entities.Order
import com.miguelangelboti.stocks.entities.OrderRequest
import com.miguelangelboti.stocks.entities.Stock
import com.miguelangelboti.stocks.utils.getUtcDatetimeAsString
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.absoluteValue

@Singleton
class StocksRepository @Inject constructor(
    @ApplicationContext context: Context
) {

    private val localDataSource = LocalDataSource(context)
    private val networkDataSource = NetworkDataSource()

    private val cacheTime = 60_000
    private var lasTimestamp = 0L

    suspend fun addOrder(request: OrderRequest) {
        Timber.d("addOrder($request)")
        val stock = getStock(request.symbol)
        require(stock != null) { "The stock was not found adding a new order!" }
        localDataSource.addOrder(Order(request, stock))
    }

    private suspend fun getStock(symbol: String): Stock? {
        Timber.d("getStock($symbol)")
        return localDataSource.getStock(symbol)
            ?: networkDataSource.getStock(symbol)?.also {
                val id = localDataSource.addStock(it)
                return it.copy(id = id)
            }
    }

    suspend fun deleteOrder(orderId: Int) {
        Timber.d("deleteOrder($orderId)")
        localDataSource.deleteOrder(orderId)
    }

    suspend fun getOrders(): List<Order> {
        Timber.d("getOrders()")
        val elapsedTime = (System.currentTimeMillis() - lasTimestamp).absoluteValue
        if (elapsedTime > cacheTime) {
            Timber.d("The cache is not valid.")
            lasTimestamp = System.currentTimeMillis()
            return localDataSource.getOrders().also { it.updatePrices() }
        }
        Timber.d("The cache is valid.")
        return localDataSource.getOrders()
    }

    private suspend fun List<Order>.updatePrices() {
        Timber.d("updatePrices()")
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
