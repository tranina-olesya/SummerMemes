package ru.vsu.summermemes.ui.main.fragments.profile

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.data.db.repositories.LocalMemeRepository
import ru.vsu.summermemes.data.sharedprefs.repositories.UserRepository
import ru.vsu.summermemes.ui.main.fragments.base.MemeListPresenter
import ru.vsu.summermemes.utils.ui.MemeShareHelper
import javax.inject.Inject

@InjectViewState
class ProfilePresenter : MemeListPresenter<ProfileView>() {

    companion object {
        const val IMAGE_URL =
            "https://static1.squarespace.com/static/58c89af95016e18d70555dca/58d8827f14fd83a16d060663/5b513bfd70a6ade9ec7d5aac/1532051074596/dmitriy-ilkevich-441481-unsplash.jpg?format=1500w"
    }

    @Inject
    lateinit var localMemeRepository: LocalMemeRepository

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var memeShareHelper: MemeShareHelper

    private var subscription: Disposable? = null

    fun viewIsReady() {
        loadMemes()
        viewState.setupBinding(IMAGE_URL, userRepository.getUserInfo())
        viewState.showLoading()
    }

    override fun favoriteButtonPressed(meme: MemeEntity, position: Int) {
        subscription = localMemeRepository
            .delete(meme)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                viewState.notifyMemeDeleted(position)
            }
    }

    private fun loadMemes() {
        subscription = localMemeRepository
            .getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doAfterTerminate {
                viewState.hideLoading()
            }
            .subscribe({
                viewState.showMemesList(it)
            }, {
                viewState.showLoadingErrorOnTopOfContent()
            })
    }

    override fun shareMeme(memeEntity: MemeEntity) {
        viewState.shareMeme(memeShareHelper.shareMemeIntent(memeEntity))
    }
}