package ru.vsu.summermemes.api.repositories

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.vsu.summermemes.api.ApiProvider
import ru.vsu.summermemes.api.NetworkService
import ru.vsu.summermemes.models.meme.MemeEntry

class MemeRepository {

    val memeAPI = ApiProvider.memeAPI

    fun getMemes(): Observable<List<MemeEntry>> {
        return memeAPI.getMemes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}