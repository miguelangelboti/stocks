package com.miguelangelboti.stocks.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prices")
class PriceEntity(
    @PrimaryKey val symbol: String,
    @ColumnInfo(name = "price") val price: Float,
    @ColumnInfo(name = "date") val date: String
)
