package ru.vsu.summermemes.ui.search

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.data.db.repositories.LocalMemeRepository
import ru.vsu.summermemes.ui.base.BasePresenter
import ru.vsu.summermemes.ui.main.fragments.base.MemeListPresenter
import java.util.Date
import javax.inject.Inject

@InjectViewState
class SearchPresenter : MemeListPresenter<SearchView>() {
    @Inject
    lateinit var localMemeRepository: LocalMemeRepository

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
                viewState.showMemesList(it)
            }, {})
    }

    override fun shareMeme(memeEntity: MemeEntity) {

    }
}