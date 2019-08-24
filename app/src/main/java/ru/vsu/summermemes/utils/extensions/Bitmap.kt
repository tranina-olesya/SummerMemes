package ru.vsu.summermemes.utils.extensions

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

fun Bitmap.convertToByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}