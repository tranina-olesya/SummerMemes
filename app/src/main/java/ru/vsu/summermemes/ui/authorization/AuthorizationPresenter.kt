package ru.vsu.summermemes.ui.authorization

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.vsu.summermemes.SummerMemesApp
import ru.vsu.summermemes.api.NetworkService
import ru.vsu.summermemes.data.sharedprefs.SharedPreferencesProvider
import ru.vsu.summermemes.models.LoginRequestEntity

@InjectViewState
class AuthorizationPresenter : MvpPresenter<AuthorizationView>() {

    private val memesAPI = NetworkService.create()

    private val sharedPreferencesProvider = SharedPreferencesProvider(SummerMemesApp.provideContext())

    private var subscription: Disposable? = null

    fun loginButtonClicked(loginRequestEntity: LoginRequestEntity) {
        if (validateFields(loginRequestEntity)) {
            loginUser(loginRequestEntity)
        }
    }

    private fun validateFields(loginRequestEntity: LoginRequestEntity): Boolean {
        val login = loginRequestEntity.login
        val password = loginRequestEntity.password

        if (login.isNotEmpty() && password.isNotEmpty() && password.length >= AuthorizationActivity.MIN_PASSWORD_LENGTH)
            return true

        if (login.isEmpty())
            viewState.showErrorForEmptyLogin()
        if (password.isEmpty())
            viewState.showErrorForEmptyPassword()
        return false
    }

    private fun loginUser(loginRequestEntity: LoginRequestEntity) {
        viewState.showLoading()
        subscription = memesAPI
                .login(loginRequestEntity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { viewState.hideLoading() }
                .subscribe(
                        { loginResponse ->
                            sharedPreferencesProvider.saveAccessToken(loginResponse.accessToken)
                            sharedPreferencesProvider.saveUserInfo(loginResponse.userInfo)
                        },
                        {
                            //error
                        }
                )
    }
}