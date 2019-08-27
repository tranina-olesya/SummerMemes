package ru.vsu.summermemes.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.Reusable
import ru.vsu.summermemes.data.db.SummerMemesDatabase
import ru.vsu.summermemes.data.db.dao.MemeDAO

@Module
object DBModule {
    @Provides
    @JvmStatic
    @Reusable
    internal fun provideSummerMemesDatabase(context: Context): SummerMemesDatabase {
        return Room.databaseBuilder(
            context,
            SummerMemesDatabase::class.java,
            "memes.db"
        ).build()
    }

    @Provides
    @JvmStatic
    internal fun provideMemeDAO(summerMemesDatabase: SummerMemesDatabase): MemeDAO {
        return summerMemesDatabase.memeDAO()
    }
}