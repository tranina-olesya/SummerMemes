package ru.vsu.summermemes.ui.main.fragments.base

import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import com.arellomobile.mvp.MvpView
import ru.vsu.summermemes.data.db.entities.MemeEntity

interface MemeListView : MvpView {
    fun showMemesList(memes: List<MemeEntity>)

    fun hideMemesList()

    fun showLoadingErrorOnTopOfContent(parentView: View)

    fun showLoadingErrorOnTopOfContent()

    fun showLoading()

    fun hideLoading()

    fun openMemeDetailActivity(meme: MemeEntity, imageView: ImageView)

    fun updateElement(meme: MemeEntity, position: Int)

    fun shareMeme(intent: Intent)
}