package com.miguelangelboti.stocks.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miguelangelboti.stocks.data.local.entities.OrderEntity
import com.miguelangelboti.stocks.data.local.entities.PriceEntity

@Database(
    entities = [
        OrderEntity::class,
        PriceEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DataBase : RoomDatabase() {

    abstract fun orderDao(): OrderDao

    abstract fun pricesDao(): PriceDao

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
