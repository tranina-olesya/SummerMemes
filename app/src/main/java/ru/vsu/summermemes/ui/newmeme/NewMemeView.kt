package ru.vsu.summermemes.ui.newmeme

import com.arellomobile.mvp.MvpView

interface NewMemeView : MvpView {
    fun showErrorSavingMeme()

    fun closeActivity()

    fun isCreateEnabled(isEnabled: Boolean)
}