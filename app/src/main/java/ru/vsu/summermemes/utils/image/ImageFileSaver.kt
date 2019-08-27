package ru.vsu.summermemes.utils.image

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ImageFileSaver @Inject constructor(val context: Context) {
    private companion object Constants {
        const val IMAGES_DIR = "MemeImages"
    }

    fun saveImageBitmap(bitmap: Bitmap, name: String): Observable<String> {
        return Observable
            .just(saveImageBitmapToInternalStorage(bitmap, name))
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
    }

    private fun saveImageBitmapToInternalStorage(bitmap: Bitmap, name: String): String {
        val contextWrapper = ContextWrapper(context)
        val directory = contextWrapper.getDir(IMAGES_DIR, Context.MODE_PRIVATE)
        val file = File(directory, "%s.png".format(name))

        FileOutputStream(file).use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            return file.absolutePath
        }
    }

}