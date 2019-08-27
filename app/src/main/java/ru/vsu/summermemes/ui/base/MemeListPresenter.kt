package ru.vsu.summermemes.ui.base

import android.graphics.Bitmap
import ru.vsu.summermemes.data.db.entities.MemeEntity

interface MemeListPresenter {
    fun memeChosen(memeEntity: MemeEntity, bitmap: Bitmap?)
}