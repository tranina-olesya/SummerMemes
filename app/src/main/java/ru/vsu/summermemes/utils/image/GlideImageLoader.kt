package ru.vsu.summermemes.utils.image

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.File

object GlideImageLoader {
    fun loadImage(url: String, view: ImageView) {
        Glide
            .with(view.context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(view)
    }

    fun loadRoundImage(url: String, view: ImageView) {
        Glide
            .with(view.context)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(view)
    }

    fun loadImageFromFiles(uri: String, view: ImageView) {
        Glide
            .with(view.context)
            .load(File(uri))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(view)
    }
}