package ru.vsu.summermemes.ui.main.fragments.feed

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.vsu.summermemes.api.repositories.MemeRepository
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.ui.main.fragments.base.MemeListPresenter
import ru.vsu.summermemes.utils.extensions.convertToMemeEntities
import ru.vsu.summermemes.utils.ui.MemeShareHelper
import javax.inject.Inject

@InjectViewState
class FeedPresenter : MemeListPresenter<FeedView>() {

    @Inject
    lateinit var memeRepository: MemeRepository

    @Inject
    lateinit var memeShareHelper: MemeShareHelper

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
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnTerminate {
                viewState.hideLoading()
            }
            .map {
                return@map it.convertToMemeEntities()
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

    override fun shareMeme(memeEntity: MemeEntity) {
        viewState.shareMeme(memeShareHelper.shareMemeIntent(memeEntity))
    }
}