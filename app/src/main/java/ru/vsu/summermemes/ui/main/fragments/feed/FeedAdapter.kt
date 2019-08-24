package ru.vsu.summermemes.ui.main.fragments.feed

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.vsu.summermemes.R
import ru.vsu.summermemes.databinding.MemeItemBinding
import ru.vsu.summermemes.models.meme.MemeEntry

class FeedAdapter(context: Context, val presenter: FeedPresenter) :
    RecyclerView.Adapter<FeedAdapter.MemeViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    var memeList = listOf<MemeEntry>()
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
        viewHolder.bind(memeList[position], presenter)
    }

    class MemeViewHolder(val binding: MemeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(meme: MemeEntry, presenter: FeedPresenter) {
            binding.meme = meme
            binding.presenter = presenter
        }
    }
}