package ru.vsu.summermemes.ui.authorization

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface AuthorizationView : MvpView {
    fun initUI()

    fun showErrorForEmptyLogin()

    fun showErrorForEmptyPassword()

    fun showLoading()

    fun hideLoading()
}