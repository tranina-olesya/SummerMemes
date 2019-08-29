package ru.vsu.summermemes.ui.memedetail

import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.data.db.repositories.LocalMemeRepository
import ru.vsu.summermemes.ui.base.BasePresenter
import ru.vsu.summermemes.utils.date.DateConvertHelper
import ru.vsu.summermemes.utils.ui.MemeShareHelper
import javax.inject.Inject

@InjectViewState
class MemeDetailPresenter : BasePresenter<MemeDetailView>() {

    var memeEntity: MemeEntity? = null
    var imageBitmap: Bitmap? = null

    @Inject
    lateinit var localMemeRepository: LocalMemeRepository

    @Inject
    lateinit var memeShareHelper: MemeShareHelper

    private var subscription: Disposable? = null

    fun viewIsReady() {
        memeEntity?.let { memeEntity ->
            if (memeEntity.meme.description.isEmpty()) {
                viewState.hideDescription()
            }
            val date =
                DateConvertHelper.getDaysAgoCreated(memeEntity.meme.createdDate)

            viewState.initMeme(memeEntity, date)
            setMemeImage()
        }
    }

    private fun setMemeImage() {
        if (imageBitmap != null)
            viewState.setImageFromBitmap(imageBitmap!!)
        else
            downloadImage()
    }

    private fun downloadImage() {
        memeEntity?.meme?.photoUrl?.let {
            viewState.loadImageFromURL(it)
        }
        memeEntity?.imagePath?.let {
            viewState.loadImageFromFiles(it)
        }
    }

    fun shareMeme() {
        memeEntity ?: return
        viewState.shareMeme(memeShareHelper.shareMemeIntent(memeEntity!!))
    }

    fun favoriteButtonClicked() {
        memeEntity ?: return
        if (memeEntity!!.isLocal && memeEntity!!.meme.isFavorite) {
            deleteFromDatabase()
        } else {
            memeEntity!!.meme.isFavorite = !memeEntity!!.meme.isFavorite
            viewState.updateButtonImage(memeEntity!!.meme.isFavorite)
        }
    }

    private fun deleteFromDatabase() {
        memeEntity ?: return
        subscription = localMemeRepository
            .delete(memeEntity!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.closeActivity()
            }, {})
    }
}