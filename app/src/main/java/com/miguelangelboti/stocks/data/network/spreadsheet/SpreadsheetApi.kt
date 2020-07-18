package com.miguelangelboti.stocks.data.network.spreadsheet

import com.miguelangelboti.stocks.data.network.spreadsheet.results.StockInfoResult
import retrofit2.http.GET
import retrofit2.http.Query

interface SpreadsheetApi {

    @GET("exec?function=DETAIL")
    suspend fun getStockInfo(@Query("symbols") symbols: String): List<StockInfoResult>

    @GET("exec?function=SEARCH")
    suspend fun searchSymbol(@Query("keyword") keywords: String): List<String>
}
