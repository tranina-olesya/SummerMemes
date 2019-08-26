package ru.vsu.summermemes.ui.main.base

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.graphics.drawable.BitmapDrawable
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.meme_item.view.*
import ru.vsu.summermemes.R
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.databinding.MemeItemBinding
import ru.vsu.summermemes.models.meme.MemeEntry
import ru.vsu.summermemes.ui.main.fragments.feed.FeedPresenter

class FeedAdapter(context: Context, var presenter: MemeListPresenter? = null) :
    RecyclerView.Adapter<FeedAdapter.MemeViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    var memeList = listOf<MemeEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = memeList.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MemeViewHolder {
        val binding =
            DataBindingUtil.inflate<MemeItemBinding>(inflater, R.layout.meme_item, viewGroup, false)
        return MemeViewHolder(binding, presenter)
    }

    override fun onBindViewHolder(viewHolder: MemeViewHolder, position: Int) {
        viewHolder.bind(memeList[position])
    }

    class MemeViewHolder(val binding: MemeItemBinding, var presenter: MemeListPresenter? = null) : RecyclerView.ViewHolder(binding.root) {

        fun bind(memeEntity: MemeEntity) {
            binding.memeEntry = memeEntity
            binding.root.setOnClickListener {
                val bitmap = (binding.root.meme_image.drawable as? BitmapDrawable)?.bitmap
                presenter?.memeChosen(memeEntity, bitmap)
            }
        }
    }
}