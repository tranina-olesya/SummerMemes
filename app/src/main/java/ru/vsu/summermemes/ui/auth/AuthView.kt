package ru.vsu.summermemes.ui.auth

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
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