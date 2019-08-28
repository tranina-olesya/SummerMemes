package ru.vsu.summermemes.ui.newmeme

import android.Manifest
import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.data.db.repositories.LocalMemeRepository
import ru.vsu.summermemes.models.meme.MemeInfo
import ru.vsu.summermemes.ui.base.BasePresenter
import ru.vsu.summermemes.utils.PermissionConstants
import ru.vsu.summermemes.utils.date.DateUtils
import ru.vsu.summermemes.utils.image.ImageFileSaver
import java.util.*
import javax.inject.Inject

@InjectViewState
class NewMemePresenter : BasePresenter<NewMemeView>() {

    @Inject
    lateinit var imageFileRepository: ImageFileSaver

    @Inject
    lateinit var memeRepository: LocalMemeRepository

    var subscription: Disposable? = null

    var image: Bitmap? = null
        set(value) {
            field = value
            if (value == null)
                viewState.hideMemeImage()
            else {
                viewState.showMemeImage()
                viewState.setMemeImage(value)
            }
            updateButtonEnabledState()
        }

    var title: String? = null
        set(value) {
            field = value
            updateButtonEnabledState()
        }

    fun saveButtonPressed(title: String?, description: String?, bitmap: Bitmap?) {
        if (title != null && title.isNotEmpty() && title.length <= NewMemeActivity.MAX_TITLE_LENGTH &&
            description != null && description.length <= NewMemeActivity.MAX_DESCRIPTION_LENGTH &&
            bitmap != null
        ) {
            val memeEntry =
                MemeInfo(null, title, description, true, DateUtils.getCurrentSeconds(), null)

            val memeEntity = MemeEntity()
            memeEntity.meme = memeEntry
            memeEntity.isLocal = true

            saveMeme(memeEntity, bitmap)
        }
    }

    private fun saveMeme(memeEntity: MemeEntity, bitmap: Bitmap) {
        subscription = imageFileRepository
            .saveImageBitmap(bitmap, UUID.randomUUID().toString())
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
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
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                viewState.closeActivity()
            }, {
                viewState.showErrorSavingMeme()
            })
    }

    fun updateButtonEnabledState() {
        if (title.isNullOrEmpty() || image == null) {
            viewState.isCreateEnabled(false)
        } else
            viewState.isCreateEnabled(true)
    }

    fun cameraButtonClicked() {
        viewState.checkPermission(Manifest.permission.CAMERA)
    }

    fun galleryButtonClicked() {
        viewState.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    fun permissionGranted(permissionName: String) {
        when (permissionName) {
            Manifest.permission.CAMERA ->
                viewState.openCamera()
            Manifest.permission.READ_EXTERNAL_STORAGE ->
                viewState.openGallery()
        }
    }

    fun permissionNotGranted(permissionName: String) {
        when (permissionName) {
            Manifest.permission.CAMERA ->
                viewState.requestPermission(
                    permissionName,
                    PermissionConstants.PERMISSION_REQUEST_CAMERA
                )
            Manifest.permission.READ_EXTERNAL_STORAGE ->
                viewState.requestPermission(
                    permissionName,
                    PermissionConstants.PERMISSION_REQUEST_READ_EXTERNAL_STORAGE
                )
        }
    }

    fun permissionDenied(permissionName: String) {

    }
}