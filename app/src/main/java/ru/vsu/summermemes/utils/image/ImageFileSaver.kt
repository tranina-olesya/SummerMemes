package ru.vsu.summermemes.utils.image

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.os.Environment
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
    }

    private fun saveImageBitmapToInternalStorage(bitmap: Bitmap, name: String): String {
        val directory = context.getExternalFilesDir(IMAGES_DIR)
        val file = File(directory, "${name}.png")

        FileOutputStream(file).use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            return file.absolutePath
        }
    }

}