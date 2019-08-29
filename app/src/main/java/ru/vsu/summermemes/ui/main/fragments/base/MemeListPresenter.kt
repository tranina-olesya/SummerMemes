package ru.vsu.summermemes.ui.main.fragments.base

import android.graphics.Bitmap
import android.widget.ImageView
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.ui.base.BasePresenter
import ru.vsu.summermemes.utils.ui.MemeShareHelper
import javax.inject.Inject

abstract class MemeListPresenter<V : MemeListView> : BasePresenter<V>() {

    fun memeChosen(memeEntity: MemeEntity, imageView: ImageView) {
        viewState.openMemeDetailActivity(memeEntity, imageView)
    }

    open fun favoriteButtonPressed(meme: MemeEntity, position: Int) {
        meme.meme.isFavorite = !meme.meme.isFavorite
        viewState.updateElement(meme, position)
    }

    abstract fun shareMeme(memeEntity: MemeEntity)
}