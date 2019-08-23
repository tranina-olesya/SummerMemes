package ru.vsu.summermemes.ui.main.fragments.feed

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import ru.vsu.summermemes.api.repositories.MemeRepository

@InjectViewState
class FeedPresenter : MvpPresenter<FeedView>() {

    private val memeRepository = MemeRepository()

    private var subscription: Disposable? = null

    fun viewIsReady() {
        loadMemes()
    }

    fun loadMemes() {
        viewState.showLoading()
        subscription = memeRepository
            .getMemes()
            .doOnTerminate {
                viewState.hideLoading()
            }
            .subscribe(
                { memeList ->
                    viewState.hideLoadingError()
                    viewState.showMemesList(memeList)
                }, {
                    viewState.hideMemesList()
                    viewState.showLoadingError()
                })
    }
}