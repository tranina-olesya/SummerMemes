package ru.vsu.summermemes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.vsu.summermemes.data.db.dao.MemeDAO
import ru.vsu.summermemes.data.db.entities.MemeEntity

@Database(entities = [MemeEntity::class], version = 1)
abstract class SummerMemesDatabase : RoomDatabase() {
    abstract fun memeDAO(): MemeDAO
}