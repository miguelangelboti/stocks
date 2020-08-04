package com.miguelangelboti.stocks.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.miguelangelboti.stocks.entities.Order
import com.miguelangelboti.stocks.entities.Stock

@Entity(tableName = "stocks")
class StockEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "region") val region: String,
    @ColumnInfo(name = "marketOpen") val marketOpen: String,
    @ColumnInfo(name = "marketClose") val marketClose: String,
    @ColumnInfo(name = "timeZone") val timeZone: String,
    @ColumnInfo(name = "currency") val currency: String,
    @ColumnInfo(name = "price") val price: Float,
    @ColumnInfo(name = "price_open") val priceOpen: Float,
    @ColumnInfo(name = "price_date") val priceDate: String,
    @ColumnInfo(name = "icon") val icon: String
)

fun Stock.toEntity(): StockEntity {
    return StockEntity(
        id = id,
        symbol = symbol,
        name = name,
        region = region,
        marketOpen = marketOpen,
        marketClose = marketClose,
        timeZone = timeZone,
        currency = currency,
        price = price,
        priceOpen = priceOpen,
        priceDate = priceDate,
        icon = icon
    )
}

fun StockEntity.toDomain(orders: List<Order>): Stock {
    return Stock(
        id = id,
        symbol = symbol,
        name = name,
        region = region,
        marketOpen = marketOpen,
        marketClose = marketClose,
        timeZone = timeZone,
        currency = currency,
        price = price,
        priceOpen = priceOpen,
        priceDate = priceDate,
        icon = icon,
        orders = orders
    )
}
