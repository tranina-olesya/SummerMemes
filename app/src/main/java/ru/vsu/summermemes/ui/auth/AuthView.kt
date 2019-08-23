package ru.vsu.summermemes.ui.auth

import com.arellomobile.mvp.MvpView

interface AuthView : MvpView {
    fun showErrorForEmptyLogin()

    fun showErrorForEmptyPassword()

    fun showLoading()

    fun hideLoading()

    fun showAuthError()

    fun openMainActivity()

    fun showPassword()

    fun hidePassword()
}