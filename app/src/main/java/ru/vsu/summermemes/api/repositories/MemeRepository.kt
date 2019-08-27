package ru.vsu.summermemes.api.repositories

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.vsu.summermemes.api.ApiProvider
import ru.vsu.summermemes.api.NetworkService
import ru.vsu.summermemes.api.interfaces.MemeAPI
import ru.vsu.summermemes.models.meme.MemeEntry
import javax.inject.Inject

class MemeRepository @Inject constructor(val memeAPI: MemeAPI) {

    fun getMemes(): Observable<List<MemeEntry>> {
        return memeAPI.getMemes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}