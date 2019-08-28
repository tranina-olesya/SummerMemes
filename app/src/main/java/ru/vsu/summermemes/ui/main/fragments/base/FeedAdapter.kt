package ru.vsu.summermemes.ui.main.fragments.base

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.meme_item.view.*
import ru.vsu.summermemes.R
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.databinding.MemeItemBinding

class FeedAdapter(
    context: Context,
    val onClickListener: (element: MemeEntity, bitmap: Bitmap?, imageView: ImageView) -> Unit,
    val onFavoriteClickListener: (element: MemeEntity, position: Int) -> Unit,
    val onShareClickListener: (element: MemeEntity) -> Unit
) : RecyclerView.Adapter<FeedAdapter.MemeViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    var memeList: MutableList<MemeEntity> = mutableListOf<MemeEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = memeList.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MemeViewHolder {
        val binding =
            DataBindingUtil.inflate<MemeItemBinding>(inflater, R.layout.meme_item, viewGroup, false)
        return MemeViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: MemeViewHolder, position: Int) {
        viewHolder.bind(memeList[position], position)
    }

    fun updateMemeFavorite(memeEntity: MemeEntity, position: Int) {
        val oldValue = memeList[position]
        oldValue.meme.isFavorite = memeEntity.meme.isFavorite
        notifyItemChanged(position, memeEntity)
    }

    fun deleteMeme(position: Int) {
        memeList.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class MemeViewHolder(val binding: MemeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(memeEntity: MemeEntity, position: Int) {
            binding.memeEntity = memeEntity

            binding.root.setOnClickListener {
                val bitmap = (binding.root.meme_image.drawable as? BitmapDrawable)?.bitmap
                onClickListener.invoke(memeEntity, bitmap, binding.memeImage)
            }

            binding.favoriteButton.setOnClickListener {
                onFavoriteClickListener.invoke(memeEntity, position)
            }

            binding.shareButton.setOnClickListener {
                onShareClickListener.invoke(memeEntity)
            }
        }
    }
}