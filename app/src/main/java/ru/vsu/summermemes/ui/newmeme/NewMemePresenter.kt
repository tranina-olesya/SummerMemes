package ru.vsu.summermemes.ui.newmeme

import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.Disposable
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.data.db.repositories.LocalMemeRepository
import ru.vsu.summermemes.models.meme.MemeEntry
import ru.vsu.summermemes.utils.DateUtils
import ru.vsu.summermemes.utils.ImageFileSaver
import java.util.*

@InjectViewState
class NewMemePresenter : MvpPresenter<NewMemeView>() {

    val imageFileRepository = ImageFileSaver()
    val memeRepository = LocalMemeRepository()

    var subscription: Disposable? = null

    fun saveButtonPressed(title: String, description: String, bitmap: Bitmap) {
        if (title.length <= NewMemeActivity.MAX_TITLE_LENGTH && description.length <= NewMemeActivity.MAX_DESCRIPTION_LENGTH) {
            val memeEntry =
                MemeEntry(null, title, description, true, DateUtils.getCurrentSeconds(), null)

            val memeEntity = MemeEntity()
            memeEntity.meme = memeEntry

            saveMeme(memeEntity, bitmap)
        }
    }

    private fun saveMeme(memeEntity: MemeEntity, bitmap: Bitmap) {
        subscription = imageFileRepository
            .saveImageBitmap(bitmap, UUID.randomUUID().toString())
            .subscribe({
                memeEntity.imagePath = it
                saveMemeEntityToDatabase(memeEntity)
            }, {
                viewState.showErrorSavingMeme()
            })
    }

    private fun saveMemeEntityToDatabase(memeEntity: MemeEntity) {
        subscription = memeRepository
            .insert(memeEntity)
            .subscribe({
                viewState.closeActivity()
            }, {
                viewState.showErrorSavingMeme()
            })
    }
}