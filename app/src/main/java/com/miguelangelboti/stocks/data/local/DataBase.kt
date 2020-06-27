package com.miguelangelboti.stocks.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miguelangelboti.stocks.data.local.entities.OrderEntity
import com.miguelangelboti.stocks.data.local.entities.StockEntity

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        StockEntity::class,
        OrderEntity::class
    ]
)
abstract class DataBase : RoomDatabase() {

    abstract fun stockDao(): StockDao

    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getDatabase(context: Context): DataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                return Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "database"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}
