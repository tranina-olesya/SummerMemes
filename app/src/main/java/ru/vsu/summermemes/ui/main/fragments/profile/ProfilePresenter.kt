package ru.vsu.summermemes.ui.main.fragments.profile

import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.data.db.repositories.LocalMemeRepository
import ru.vsu.summermemes.ui.main.base.MemeListPresenter
import ru.vsu.summermemes.utils.extensions.convertToByteArray

@InjectViewState
class ProfilePresenter : MvpPresenter<ProfileView>(), MemeListPresenter {

    private val localMemeRepository = LocalMemeRepository()

    private var subscription: Disposable? = null

    fun viewIsReady() {
        loadMemes()
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