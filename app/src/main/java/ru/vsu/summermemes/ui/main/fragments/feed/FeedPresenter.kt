package ru.vsu.summermemes.ui.main.fragments.feed

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import ru.vsu.summermemes.api.repositories.MemeRepository
import ru.vsu.summermemes.models.meme.MemeEntry

@InjectViewState
class FeedPresenter : MvpPresenter<FeedView>() {

    private val memeRepository = MemeRepository()

    private var subscription: Disposable? = null

    fun viewIsReady() {
        loadMemes()
    }

    fun refreshMemes() {
        viewState.hideMemesList()
        viewState.hideLoadingError()
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

    fun memeChosen(meme: MemeEntry) {
        viewState.openMemeDetailActivity(meme)
    }
}