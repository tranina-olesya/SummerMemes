package ru.vsu.summermemes.ui.memedetail

import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.ui.base.BasePresenter
import ru.vsu.summermemes.utils.date.DateConvertHelper

@InjectViewState
class MemeDetailPresenter : BasePresenter<MemeDetailView>() {

    var memeEntity: MemeEntity? = null
    var imageBitmap: Bitmap? = null

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
}