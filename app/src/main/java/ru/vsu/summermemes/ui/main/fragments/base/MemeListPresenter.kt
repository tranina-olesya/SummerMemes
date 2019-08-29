package ru.vsu.summermemes.ui.main.fragments.base

import android.widget.ImageView
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.ui.base.BasePresenter

abstract class MemeListPresenter<V : MemeListView> : BasePresenter<V>() {

    var results: MutableList<MemeEntity>? = null

    fun memeChosen(memeEntity: MemeEntity, imageView: ImageView) {
        viewState.openMemeDetailActivity(memeEntity, imageView)
    }

    open fun favoriteButtonPressed(meme: MemeEntity, position: Int?) {
        meme.meme.isFavorite = !meme.meme.isFavorite
        position ?: return
        viewState.updateElement(meme, position)
    }

    abstract fun shareMeme(memeEntity: MemeEntity)
}