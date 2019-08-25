package ru.vsu.summermemes.databinding

import androidx.databinding.BindingAdapter
import android.widget.ImageView
import ru.vsu.summermemes.utils.image.GlideImageLoader

object ImageBindingAdapter {

    @JvmStatic
    @BindingAdapter("url")
    fun getImageFromUrl(view: ImageView, url: String?) {
        url?.let {
            GlideImageLoader.loadImage(url, view)
        }
    }

    @JvmStatic
    @BindingAdapter("urlRoundImage")
    fun getRoundImageFromUrl(view: ImageView, url: String?) {
        url?.let {
            GlideImageLoader.loadRoundImage(url, view)
        }
    }
}