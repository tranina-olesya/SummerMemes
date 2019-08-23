package ru.vsu.summermemes.ui.auth

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import ru.vsu.summermemes.api.repositories.AuthRepository
import ru.vsu.summermemes.data.sharedprefs.repositories.UserRepository
import ru.vsu.summermemes.models.auth.AuthRequestEntity

@InjectViewState
class AuthPresenter : MvpPresenter<AuthView>() {

    private val authRepository = AuthRepository()
    private val userRepository = UserRepository()

    private var subscription: Disposable? = null

    var isPasswordVisible = false

    fun passwordHideButtonClicked() {
        if (isPasswordVisible) {
            viewState.hidePassword()
        } else {
            viewState.showPassword()
        }
        isPasswordVisible = !isPasswordVisible
    }

    fun loginButtonClicked(authRequestEntity: AuthRequestEntity) {
        if (validateFields(authRequestEntity)) {
            authUser(authRequestEntity)
        }
    }

    private fun validateFields(authRequestEntity: AuthRequestEntity): Boolean {
        val login = authRequestEntity.login
        val password = authRequestEntity.password

        if (login.isNotEmpty() && password.isNotEmpty() && password.length >= AuthActivity.MIN_PASSWORD_LENGTH)
            return true

        if (login.isEmpty())
            viewState.showErrorForEmptyLogin()
        if (password.isEmpty())
            viewState.showErrorForEmptyPassword()
        return false
    }

    private fun authUser(authRequestEntity: AuthRequestEntity) {
        viewState.showLoading()
        subscription = authRepository
            .login(authRequestEntity)
            .doOnNext { authResponse ->
                userRepository.saveAuthResponse(authResponse)
            }
            .doOnTerminate { viewState.hideLoading() }
            .subscribe(
                { authResponse ->
                    viewState.openMainActivity()
                },
                {
                    viewState.showAuthError()
                }
            )
    }
}