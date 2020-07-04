package com.miguelangelboti.stocks.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.miguelangelboti.stocks.entities.Order

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

fun Order.toEntity(stockId: Int): OrderEntity {
    return OrderEntity(
        stockId = stockId,
        stocks = stocks,
        price = price
    )
}

fun toDomain(entity: OrderEntity): Order {
    return Order(entity.id, entity.stocks, entity.price)
}

fun List<OrderEntity>.toDomain(): List<Order> {
    return map { toDomain(it) }
}
