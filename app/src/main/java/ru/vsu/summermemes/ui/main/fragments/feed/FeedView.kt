package ru.vsu.summermemes.ui.main.fragments.feed

import com.arellomobile.mvp.MvpView
import ru.vsu.summermemes.data.db.entities.MemeEntity

interface FeedView : MvpView {
    fun showMemesList(memes: List<MemeEntity>)

    fun hideMemesList()

    fun showLoadingError()

    fun hideLoadingError()

    fun showLoadingErrorOnTopOfContent()

    fun showLoading()

    fun hideLoading()

    fun openMemeDetailActivity(meme: MemeEntity, byteArray: ByteArray?)
}