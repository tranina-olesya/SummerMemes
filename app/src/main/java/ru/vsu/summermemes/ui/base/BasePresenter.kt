package ru.vsu.summermemes.ui.base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import ru.vsu.summermemes.SummerMemesApp
import ru.vsu.summermemes.di.component.DaggerPresenterInjector
import ru.vsu.summermemes.di.component.PresenterInjector
import ru.vsu.summermemes.di.module.ContextModule
import ru.vsu.summermemes.di.module.DBModule
import ru.vsu.summermemes.di.module.NetworkModule
import ru.vsu.summermemes.ui.auth.AuthPresenter
import ru.vsu.summermemes.ui.main.MainPresenter
import ru.vsu.summermemes.ui.main.fragments.base.MemeListPresenter
import ru.vsu.summermemes.ui.main.fragments.feed.FeedPresenter
import ru.vsu.summermemes.ui.main.fragments.profile.ProfilePresenter
import ru.vsu.summermemes.ui.memedetail.MemeDetailPresenter
import ru.vsu.summermemes.ui.newmeme.NewMemePresenter
import ru.vsu.summermemes.ui.search.SearchPresenter

open class BasePresenter<V : MvpView>: MvpPresenter<V>() {

    private val injector: PresenterInjector = DaggerPresenterInjector
        .builder()
        .contextModule(ContextModule(SummerMemesApp.appInstance))
        .networkModule(NetworkModule)
        .dbModule(DBModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is AuthPresenter -> injector.inject(this)
            is MainPresenter -> injector.inject(this)
            is FeedPresenter -> injector.inject(this)
            is ProfilePresenter -> injector.inject(this)
            is NewMemePresenter -> injector.inject(this)
            is MemeDetailPresenter -> injector.inject(this)
            is SearchPresenter -> injector.inject(this)
        }
    }

}