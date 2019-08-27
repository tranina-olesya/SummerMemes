package ru.vsu.summermemes.ui.main.fragments.base

import android.graphics.Bitmap
import android.view.View
import com.arellomobile.mvp.MvpView
import ru.vsu.summermemes.data.db.entities.MemeEntity

interface MemeListView: MvpView {
    fun showMemesList(memes: List<MemeEntity>)

    fun hideMemesList()

    fun showLoadingErrorOnTopOfContent(parentView: View)

    fun showLoadingErrorOnTopOfContent()

    fun showLoading()

    fun hideLoading()

    fun openMemeDetailActivity(meme: MemeEntity, bitmap: Bitmap?)
}