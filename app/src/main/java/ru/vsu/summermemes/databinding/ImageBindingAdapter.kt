package ru.vsu.summermemes.databinding

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object ImageBindingAdapter {

    @JvmStatic
    @BindingAdapter("url")
    fun getImageFromUrl(view: ImageView, url: String?) {
        url.let {
            Glide
                .with(view.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view)
        }
    }

}