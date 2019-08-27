package ru.vsu.summermemes.ui.main.fragments.feed

import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import ru.vsu.summermemes.SummerMemesApp
import ru.vsu.summermemes.api.repositories.MemeRepository
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.di.component.DaggerPresenterInjector
import ru.vsu.summermemes.di.module.ContextModule
import ru.vsu.summermemes.di.module.NetworkModule
import ru.vsu.summermemes.ui.base.BasePresenter
import ru.vsu.summermemes.ui.base.MemeListPresenter
import ru.vsu.summermemes.utils.extensions.convertToByteArray
import ru.vsu.summermemes.utils.extensions.convertToMemeEntities
import javax.inject.Inject

@InjectViewState
class FeedPresenter : BasePresenter<FeedView>(), MemeListPresenter {

    @Inject
    lateinit var memeRepository: MemeRepository

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
        viewState.openMemeDetailActivity(memeEntity, bitmap?.convertToByteArray())
    }
}