package ru.vsu.summermemes.ui.main.fragments.base

import android.graphics.Bitmap
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.ui.base.BasePresenter
import ru.vsu.summermemes.utils.extensions.convertToByteArray

open class MemeListPresenter<V: MemeListView> : BasePresenter<V>() {
    fun memeChosen(memeEntity: MemeEntity, bitmap: Bitmap?) {
        viewState.openMemeDetailActivity(memeEntity, bitmap?.convertToByteArray())
    }
}