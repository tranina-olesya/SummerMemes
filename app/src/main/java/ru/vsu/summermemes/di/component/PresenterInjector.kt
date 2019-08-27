package ru.vsu.summermemes.di.component

import dagger.Component
import ru.vsu.summermemes.di.module.ContextModule
import ru.vsu.summermemes.di.module.DBModule
import ru.vsu.summermemes.di.module.NetworkModule
import ru.vsu.summermemes.ui.auth.AuthPresenter
import ru.vsu.summermemes.ui.main.MainPresenter
import ru.vsu.summermemes.ui.main.fragments.feed.FeedPresenter
import ru.vsu.summermemes.ui.main.fragments.profile.ProfilePresenter
import ru.vsu.summermemes.ui.newmeme.NewMemePresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, NetworkModule::class, DBModule::class])
interface PresenterInjector {
    fun inject(authPresenter: AuthPresenter)

    fun inject(mainPresenter: MainPresenter)

    fun inject(feedPresenter: FeedPresenter)

    fun inject(profilePresenter: ProfilePresenter)

    fun inject(newMemePresenter: NewMemePresenter)

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector

        fun contextModule(contextModule: ContextModule): Builder

        fun networkModule(networkModule: NetworkModule): Builder

        fun dbModule(dbModule: DBModule): Builder
    }
}