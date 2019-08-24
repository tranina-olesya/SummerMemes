package ru.vsu.summermemes.ui.main.fragments.feed

import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import ru.vsu.summermemes.api.repositories.MemeRepository
import ru.vsu.summermemes.models.meme.MemeEntry
import ru.vsu.summermemes.utils.extensions.convertToByteArray

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

    fun memeChosen(meme: MemeEntry, bitmap: Bitmap?) {
        viewState.openMemeDetailActivity(meme, bitmap?.convertToByteArray())
    }
}