package ru.vsu.summermemes.api.repositories

import io.reactivex.Observable
import ru.vsu.summermemes.api.interfaces.MemeAPI
import ru.vsu.summermemes.models.meme.MemeInfo
import javax.inject.Inject

class MemeRepository @Inject constructor(val memeAPI: MemeAPI) {

    fun getMemes(): Observable<List<MemeInfo>> {
        return memeAPI.getMemes()
    }
}