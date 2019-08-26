package ru.vsu.summermemes.ui.main.fragments.feed

import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import ru.vsu.summermemes.api.repositories.MemeRepository
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.models.meme.MemeEntry
import ru.vsu.summermemes.ui.main.base.MemeListPresenter
import ru.vsu.summermemes.utils.extensions.convertToByteArray
import ru.vsu.summermemes.utils.extensions.convertToMemeEntities

@InjectViewState
class FeedPresenter : MvpPresenter<FeedView>(), MemeListPresenter {

    private val memeRepository = MemeRepository()

    private var subscription: Disposable? = null

    private var memeList: List<MemeEntity> = listOf()

    fun viewIsReady() {
        loadMemes()
    }

    fun refreshMemes() {
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
            .flatMap {
                return@flatMap Observable.just(it.convertToMemeEntities())
            }
            .subscribe(
                { memeList ->
                    this.memeList = memeList
                    viewState.hideLoadingError()
                    viewState.showMemesList(memeList)
                }, {
                    if (memeList.isEmpty())
                        viewState.showLoadingError()
                    else
                        viewState.showLoadingErrorOnTopOfContent()
                })
    }


    override fun memeChosen(memeEntity: MemeEntity, bitmap: Bitmap?) {
        viewState.openMemeDetailActivity(memeEntity.meme, bitmap?.convertToByteArray())
    }
}