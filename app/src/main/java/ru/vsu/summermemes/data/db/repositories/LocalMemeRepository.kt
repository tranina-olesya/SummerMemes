package ru.vsu.summermemes.data.db.repositories

import io.reactivex.Completable
import io.reactivex.Single
import ru.vsu.summermemes.data.db.dao.MemeDAO
import ru.vsu.summermemes.data.db.entities.MemeEntity
import javax.inject.Inject

class LocalMemeRepository @Inject constructor(val memeDAO: MemeDAO) {

    fun insert(memeEntity: MemeEntity): Completable {
        return memeDAO
            .insert(memeEntity)
    }

    fun getAll(): Single<List<MemeEntity>> {
        return memeDAO
            .getAll()
    }

    fun delete(memeEntity: MemeEntity): Completable {
        return memeDAO.delete(memeEntity)
    }
}