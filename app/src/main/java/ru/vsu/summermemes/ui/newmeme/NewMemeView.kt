package ru.vsu.summermemes.ui.newmeme

import com.arellomobile.mvp.MvpView

interface NewMemeView : MvpView {
    fun showErrorSavingMeme()

    fun closeActivity()

    fun isCreateEnabled(isEnabled: Boolean)

    fun hideMemeImage()

    fun showMemeImage()

    fun checkPermission(permissionName: String)

    fun requestPermission(permissionName: String, permissionConstant: Int)

    fun openCamera()
}