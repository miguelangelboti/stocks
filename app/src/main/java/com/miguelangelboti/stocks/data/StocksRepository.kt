package com.miguelangelboti.stocks.data

import android.content.Context
import com.miguelangelboti.stocks.data.local.LocalDataSource
import com.miguelangelboti.stocks.data.network.NetworkDataSource
import com.miguelangelboti.stocks.entities.Order
import com.miguelangelboti.stocks.entities.OrderRequest
import com.miguelangelboti.stocks.entities.Stock
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
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
        localDataSource.addOrder(stock.id, Order(request))
    }

    suspend fun deleteOrder(orderId: Int) {
        Timber.d("deleteOrder($orderId)")
        localDataSource.deleteOrder(orderId)
    }

    suspend fun getStock(stockId: Int): Stock? {
        Timber.d("getStock($stockId)")
        return localDataSource.getStock(stockId)
    }

    fun getStocks(): Flow<List<Stock>> {
        Timber.d("getStocks()")
        return localDataSource.getStocksFlow()
    }

    private suspend fun getStock(symbol: String): Stock? {
        Timber.d("getStock($symbol)")
        return localDataSource.getStock(symbol)
            ?: networkDataSource.getStock(symbol)?.also {
                val id = localDataSource.addStock(it)
                return it.copy(id = id)
            }
    }

    suspend fun searchSymbol(symbol: String): List<String> {
        return networkDataSource.searchSymbol(symbol)
    }

    suspend fun update() {
        Timber.d("update()")
        val elapsedTime = (System.currentTimeMillis() - lasTimestamp).absoluteValue
        if (elapsedTime > cacheTime) {
            Timber.d("The cache is not valid.")
            lasTimestamp = System.currentTimeMillis()
            val localStocks = localDataSource.getStocks()
            val updatedStock = networkDataSource.updateStocks(localStocks)
            localDataSource.updateStocks(updatedStock)
        } else {
            Timber.d("The cache is valid.")
        }
    }
}
