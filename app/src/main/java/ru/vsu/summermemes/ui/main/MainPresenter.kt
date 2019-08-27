package ru.vsu.summermemes.ui.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import ru.vsu.summermemes.SummerMemesApp
import ru.vsu.summermemes.api.repositories.AuthRepository
import ru.vsu.summermemes.data.sharedprefs.repositories.UserRepository
import ru.vsu.summermemes.di.component.DaggerPresenterInjector
import ru.vsu.summermemes.di.module.ContextModule
import ru.vsu.summermemes.di.module.NetworkModule
import ru.vsu.summermemes.ui.auth.AuthView
import ru.vsu.summermemes.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter : BasePresenter<MainView>() {

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var authRepository : AuthRepository

    private var subscription: Disposable? = null

    fun logout() {
        subscription = authRepository
            .logout(userRepository.getAccessToken()!!)
            .subscribe({
                viewState.openAuthScreen()
            })
    }
}