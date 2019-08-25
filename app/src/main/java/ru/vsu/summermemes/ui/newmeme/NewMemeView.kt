package ru.vsu.summermemes.ui.newmeme

import com.arellomobile.mvp.MvpView
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.models.meme.MemeEntry

interface NewMemeView: MvpView {
    fun showErrorSavingMeme()

    fun closeActivity()
}