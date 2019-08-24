package ru.vsu.summermemes.ui.main.fragments.feed

import android.graphics.Bitmap
import com.arellomobile.mvp.MvpView
import ru.vsu.summermemes.models.meme.MemeEntry

interface FeedView: MvpView {
    fun showMemesList(memes: List<MemeEntry>)

    fun hideMemesList()

    fun showLoadingError()

    fun hideLoadingError()

    fun showLoadingErrorOnTopOfContent()

    fun showLoading()

    fun hideLoading()

    fun openMemeDetailActivity(meme: MemeEntry, byteArray: ByteArray?)
}