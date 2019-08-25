package ru.vsu.summermemes.utils.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory

object BitmapConvertHelper {
    fun getBitmapFromByteArray(byteArray: ByteArray) : Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}