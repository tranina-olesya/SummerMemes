package ru.vsu.summermemes.ui.main.fragments.base

import android.graphics.Bitmap
import android.widget.ImageView
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.ui.base.BasePresenter

open class MemeListPresenter<V : MemeListView> : BasePresenter<V>() {

    fun memeChosen(memeEntity: MemeEntity, bitmap: Bitmap?, imageView: ImageView) {
        viewState.openMemeDetailActivity(memeEntity, bitmap, imageView)
    }

    fun favoriteButtonPressed(meme: MemeEntity, position: Int) {
        meme.meme.isFavorite = !meme.meme.isFavorite
        viewState.updateElement(meme, position)
    }
}