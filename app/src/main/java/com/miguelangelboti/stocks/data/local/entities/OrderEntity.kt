package com.miguelangelboti.stocks.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.miguelangelboti.stocks.entities.Order
import com.miguelangelboti.stocks.entities.Stock

@Entity(tableName = "orders")
class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "stocks") val stocks: Float,
    @ColumnInfo(name = "price") val price: Float
)

fun Order.toEntity(): OrderEntity {
    return OrderEntity(symbol = stock.symbol, stocks = stocks, price = price)
}

fun toDomain(entity: OrderEntity): Order {
    return Order(entity.id, Stock(entity.symbol), entity.stocks, entity.price)
}
