package com.andyshon.cryptocoins.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andyshon.cryptocoins.data.entity.Coin

@Database(entities = [Coin::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun coinsDao(): CoinsDAO
}