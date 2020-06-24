package com.miguelangelboti.stocks.data.network.alphavantage.results

import com.squareup.moshi.Json

data class StockPriceResult(@field:Json(name = "Global Quote") val quote: Quote)

data class Quote(
    @field:Json(name = "05. price") val price: Float
)
