package ru.vsu.summermemes.ui.search

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.data.db.repositories.LocalMemeRepository
import ru.vsu.summermemes.ui.main.fragments.base.MemeListPresenter
import ru.vsu.summermemes.utils.ui.MemeShareHelper
import javax.inject.Inject

@InjectViewState
class SearchPresenter : MemeListPresenter<SearchView>() {
    @Inject
    lateinit var localMemeRepository: LocalMemeRepository

    @Inject
    lateinit var memeShareHelper: MemeShareHelper

    private var subscription: Disposable? = null

    fun searchMeme(name: String) {
        viewState.showLoading()
        subscription = localMemeRepository
            .findMeme(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate {
                viewState.hideLoading()
            }
            .subscribe({
                results = it.toMutableList()
                if (it.isEmpty()) {
                    viewState.hideMemesList()
                    viewState.showNothingFoundMessage()
                } else {
                    viewState.hideNothingFoundMessage()
                    viewState.showMemesList(it)
                }
            }, {})
    }

    override fun shareMeme(memeEntity: MemeEntity) {
        viewState.shareMeme(memeShareHelper.shareMemeIntent(memeEntity))
    }

    override fun favoriteButtonPressed(memeEntity: MemeEntity, position: Int?) {
        results ?: return
        memeEntity.meme.isFavorite = !memeEntity.meme.isFavorite
        viewState.showMemesList(results!!)
    }
}