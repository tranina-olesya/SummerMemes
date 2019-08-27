package ru.vsu.summermemes.ui.main.fragments.profile

import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.data.db.repositories.LocalMemeRepository
import ru.vsu.summermemes.data.sharedprefs.repositories.UserRepository
import ru.vsu.summermemes.ui.base.BasePresenter
import ru.vsu.summermemes.ui.base.MemeListPresenter
import ru.vsu.summermemes.utils.extensions.convertToByteArray
import javax.inject.Inject

@InjectViewState
class ProfilePresenter : BasePresenter<ProfileView>(), MemeListPresenter {

    companion object {
        const val IMAGE_URL =
            "https://static1.squarespace.com/static/58c89af95016e18d70555dca/58d8827f14fd83a16d060663/5b513bfd70a6ade9ec7d5aac/1532051074596/dmitriy-ilkevich-441481-unsplash.jpg?format=1500w"
    }

    @Inject
    lateinit var localMemeRepository: LocalMemeRepository

    @Inject
    lateinit var userRepository: UserRepository

    private var subscription: Disposable? = null

    fun viewIsReady() {
        loadMemes()
        viewState.setupBinding(IMAGE_URL, userRepository.getUserInfo())
        viewState.showLoading()
    }

    private fun loadMemes() {
        subscription = localMemeRepository
            .getAll()
            .doAfterTerminate {
                viewState.hideLoading()
            }
            .subscribe({
                viewState.showMemes(it)
            }, {
                viewState.showLoadingError()
            })
    }

    override fun memeChosen(memeEntity: MemeEntity, bitmap: Bitmap?) {
        viewState.openMemeDetailActivity(memeEntity, bitmap?.convertToByteArray())
    }
}