package com.miguelangelboti.stocks.data.network

import com.miguelangelboti.stocks.BuildConfig
import com.miguelangelboti.stocks.data.network.spreadsheet.SpreadsheetApi
import com.miguelangelboti.stocks.data.network.spreadsheet.results.StockInfoResult
import com.miguelangelboti.stocks.data.network.spreadsheet.results.toDomain
import com.miguelangelboti.stocks.entities.Stock
import com.miguelangelboti.stocks.utils.getUtcDatetimeAsString
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit.Builder
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import okhttp3.OkHttpClient.Builder as OkHttpBuilder

class NetworkDataSource {

    private var service = Builder()
        .baseUrl("https://script.google.com/macros/s/${BuildConfig.SPREADSHEET_ID}/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(OkHttpBuilder().addInterceptor(HttpLoggingInterceptor().apply { level = BODY }).build())
        .build()
        .create(SpreadsheetApi::class.java)

    suspend fun searchSymbol(symbol: String): List<String> {
        Timber.d("searchSymbol($symbol)")
        return service.searchSymbol(symbol)
    }

    suspend fun getStock(symbol: String): Stock? {
        Timber.d("getStock($symbol)")
        return service.getStockInfo(symbol).first().toDomain()
    }

    suspend fun updateStocks(stocks: List<Stock>): List<Stock> {
        val symbols = stocks.joinToString(separator = ",") { it.symbol }
        Timber.d("updateStocks($symbols)")
        val result = service.getStockInfo(symbols)
        return stocks.map { stock -> stock.updatePrice(result.firstOrNull { stock.symbol == it.symbol }) }
    }

    private fun Stock.updatePrice(stockInfo: StockInfoResult?): Stock {
        stockInfo ?: return this
        return copy(
            price = stockInfo.price,
            priceOpen = stockInfo.priceOpen,
            priceDate = getUtcDatetimeAsString()
        )
    }
}
