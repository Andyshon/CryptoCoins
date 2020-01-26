package com.andyshon.cryptocoins.data.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andyshon.cryptocoins.data.entity.Coin
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface CoinsDAO {

    @Query("SELECT * FROM Coin")
    fun getCoins(): Observable<List<Coin>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoin(coin: Coin): Completable

    @Query("DELETE FROM Coin")
    fun deleteAll(): Completable

}