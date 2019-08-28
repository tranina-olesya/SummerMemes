package ru.vsu.summermemes.ui.memedetail

import android.content.Intent
import android.graphics.Bitmap
import com.arellomobile.mvp.MvpView
import ru.vsu.summermemes.data.db.entities.MemeEntity

interface MemeDetailView : MvpView {
    fun hideDescription()

    fun initMeme(memeEntity: MemeEntity, date: String)

    fun setImageFromBitmap(bitmap: Bitmap)

    fun loadImageFromURL(url: String)

    fun loadImageFromFiles(uri: String)

    fun shareMeme(intent: Intent)
}