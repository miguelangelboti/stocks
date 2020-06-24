package com.miguelangelboti.stocks.data.network.alphavantage

import com.miguelangelboti.stocks.BuildConfig
import com.miguelangelboti.stocks.data.network.alphavantage.results.SearchSymbolResult
import com.miguelangelboti.stocks.data.network.alphavantage.results.StockPriceResult
import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaVantageApi {

    @GET("query?function=GLOBAL_QUOTE&apikey=$API_KEY")
    suspend fun getStockPrice(@Query("symbol") symbol: String): StockPriceResult

    @GET("query?function=SYMBOL_SEARCH&apikey=$API_KEY")
    suspend fun searchSymbol(@Query("keywords") keywords: String): SearchSymbolResult

    companion object {
        private const val API_KEY = BuildConfig.ALPHAVANTAGE_API_KEY
    }
}
