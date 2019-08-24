package ru.vsu.summermemes.databinding

import androidx.databinding.BindingAdapter
import android.widget.ImageView
import ru.vsu.summermemes.utils.GlideImageLoader

object ImageBindingAdapter {

    @JvmStatic
    @BindingAdapter("url")
    fun getImageFromUrl(view: ImageView, url: String?) {
        url?.let {
            GlideImageLoader.loadImage(url, view)
        }
    }

}