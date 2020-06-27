package com.miguelangelboti.stocks.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.miguelangelboti.stocks.entities.Order
import com.miguelangelboti.stocks.entities.Stock

@Entity(
    tableName = "orders",
    foreignKeys = [
        ForeignKey(
            entity = StockEntity::class,
            parentColumns = ["id"],
            childColumns = ["stock_id"],
            onDelete = CASCADE
        )
    ]
)
class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "stock_id", index = true) val stockId: Int,
    @ColumnInfo(name = "stocks") val stocks: Float,
    @ColumnInfo(name = "price") val price: Float
)

fun Order.toEntity(): OrderEntity {
    return OrderEntity(
        stockId = stock.id,
        stocks = stocks,
        price = price
    )
}

fun toDomain(entity: OrderEntity, stock: Stock): Order {
    return Order(entity.id, stock, entity.stocks, entity.price)
}
