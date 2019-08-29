package ru.vsu.summermemes.ui.auth

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.vsu.summermemes.api.repositories.AuthRepository
import ru.vsu.summermemes.data.sharedprefs.repositories.UserRepository
import ru.vsu.summermemes.models.auth.AuthRequestEntity
import ru.vsu.summermemes.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class AuthPresenter : BasePresenter<AuthView>() {

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var userRepository: UserRepository

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
        if (login.isNotEmpty() && login != AuthActivity.PHONE_PREFIX && password.isNotEmpty() && password.length >= AuthActivity.MIN_PASSWORD_LENGTH)
            return true

        if (login.isEmpty() || login == AuthActivity.PHONE_PREFIX)
            viewState.showErrorForEmptyLogin()
        if (password.isEmpty())
            viewState.showErrorForEmptyPassword()
        return false
    }

    private fun authUser(authRequestEntity: AuthRequestEntity) {
        viewState.showLoading()
        subscription = authRepository
            .login(authRequestEntity)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnNext { authResponse ->
                userRepository.saveAuthResponse(authResponse)
            }
            .doOnTerminate { viewState.hideLoading() }
            .subscribe(
                { authResponse ->
                    viewState.openMainActivity()
                }, {
                    viewState.showAuthError()
                }
            )
    }
}