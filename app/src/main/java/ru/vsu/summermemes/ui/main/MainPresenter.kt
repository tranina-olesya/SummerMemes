package ru.vsu.summermemes.ui.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import ru.vsu.summermemes.api.repositories.AuthRepository
import ru.vsu.summermemes.data.sharedprefs.repositories.UserRepository

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    private val authRepository = AuthRepository()

    private var subscription: Disposable? = null

    fun logout() {
        subscription = authRepository
            .logout(UserRepository().getAccessToken()!!)
            .subscribe({
                viewState.openAuthScreen()
            })
    }
}