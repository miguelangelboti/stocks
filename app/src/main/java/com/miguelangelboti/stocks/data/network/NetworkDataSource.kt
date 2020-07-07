package com.miguelangelboti.stocks.data.network

import com.miguelangelboti.stocks.data.entities.StockInfo
import com.miguelangelboti.stocks.data.network.alphavantage.AlphaVantageApi
import com.miguelangelboti.stocks.data.network.alphavantage.results.toDomain
import com.miguelangelboti.stocks.entities.Stock
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit.Builder
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import okhttp3.OkHttpClient.Builder as OkHttpBuilder

class NetworkDataSource {

    private var service = Builder()
        .baseUrl("https://www.alphavantage.co/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(OkHttpBuilder().addInterceptor(HttpLoggingInterceptor().apply { level = BODY }).build())
        .build()
        .create(AlphaVantageApi::class.java)

    suspend fun getStockInfo(symbol: String): StockInfo {
        Timber.d("getStockInfo($symbol)")
        return service.getStockInfo(symbol).toDomain()
    }

    suspend fun searchSymbol(symbol: String): List<String> {
        Timber.d("searchSymbol($symbol)")
        return service.searchSymbol(symbol).toDomain()
    }

    suspend fun getStock(symbol: String): Stock? {
        Timber.d("getStock($symbol)")
        val stockInfo = getStockInfo(symbol)
        return service.searchSymbol(symbol).toDomain(stockInfo)
    }
}
