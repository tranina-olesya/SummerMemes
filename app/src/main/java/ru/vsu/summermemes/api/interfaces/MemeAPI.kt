package ru.vsu.summermemes.api.interfaces

import io.reactivex.Observable
import retrofit2.http.GET
import ru.vsu.summermemes.models.meme.MemeEntry

interface MemeAPI {
    @GET("/memes")
    fun getMemes(): Observable<List<MemeEntry>>
}