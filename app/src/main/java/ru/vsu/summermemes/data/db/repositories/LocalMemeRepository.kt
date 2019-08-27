package ru.vsu.summermemes.data.db.repositories

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.vsu.summermemes.SummerMemesApp
import ru.vsu.summermemes.data.db.dao.MemeDAO
import ru.vsu.summermemes.data.db.entities.MemeEntity
import javax.inject.Inject

class LocalMemeRepository @Inject constructor(val memeDAO: MemeDAO){

    fun insert(memeEntity: MemeEntity): Completable {
        return memeDAO
            .insert(memeEntity)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun getAll(): Single<List<MemeEntity>> {
        return memeDAO
            .getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}