package ru.vsu.summermemes.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import ru.vsu.summermemes.data.db.entities.MemeEntity

@Dao
interface MemeDAO {
    @Insert
    fun insert(memeEntity: MemeEntity): Completable

    @Query("select * from memes")
    fun getAll(): Single<List<MemeEntity>>
}