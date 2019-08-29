package ru.vsu.summermemes.ui.search.controllers

import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.vsu.summermemes.R
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.utils.image.GlideImageLoader

class MemeController(
    val onElementClickListener: (memeEntity: MemeEntity, imageView: ImageView) -> Unit,
    val onFavoriteClickListener: (memeEntity: MemeEntity) -> Unit,
    val onShareClickListener: (memeEntity: MemeEntity) -> Unit
) : BindableItemController<MemeEntity, MemeController.MemeViewHolder>() {

    override fun createViewHolder(parent: ViewGroup): MemeViewHolder = MemeViewHolder(parent)

    override fun getItemId(data: MemeEntity): String = data.id.toString()

    inner class MemeViewHolder(parent: ViewGroup) :
        BindableViewHolder<MemeEntity>(parent, R.layout.meme_item) {

        private lateinit var memeEntity: MemeEntity

        private val titleTextView: TextView = itemView.findViewById(R.id.meme_title)
        private val imageView: ImageView = itemView.findViewById(R.id.meme_image)
        private val favoriteButton: ImageButton = itemView.findViewById(R.id.favorite_button)
        private val shareButton: ImageButton = itemView.findViewById(R.id.share_button)

        init {
            itemView.setOnClickListener { onElementClickListener.invoke(memeEntity, imageView) }
            favoriteButton.setOnClickListener { onFavoriteClickListener(memeEntity) }
            shareButton.setOnClickListener { onShareClickListener.invoke(memeEntity) }
        }

        override fun bind(memeEntity: MemeEntity) {
            this.memeEntity = memeEntity
            titleTextView.text = memeEntity.meme.title

            if (memeEntity.meme.isFavorite)
                favoriteButton.setImageResource(R.drawable.ic_favorite)
            else
                favoriteButton.setImageResource(R.drawable.ic_favorite_empty)

            if (memeEntity.isLocal && memeEntity.imagePath != null)
                GlideImageLoader
                    .loadImageFromFiles(memeEntity.imagePath!!, imageView)
            else if (memeEntity.meme.photoUrl != null)
                GlideImageLoader
                    .loadImage(memeEntity.meme.photoUrl!!, imageView)
        }
    }
}