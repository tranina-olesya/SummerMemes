package ru.vsu.summermemes.ui.main.fragments.base.activity

import android.content.Intent
import android.widget.ImageView
import ru.vsu.summermemes.data.db.entities.MemeEntity

interface MemeListActivityView {
    fun openMemeDetailActivity(meme: MemeEntity, imageView: ImageView)
}