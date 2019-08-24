package ru.vsu.summermemes.ui.memedetail

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.vsu.summermemes.R
import ru.vsu.summermemes.databinding.ActivityMemeDetailBinding
import ru.vsu.summermemes.models.meme.MemeEntry

class MemeDetailActivity : AppCompatActivity() {

    companion object {
        const val MEME_EXTRA = "MEME"
    }

    private lateinit var binding: ActivityMemeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meme_detail)
        val meme = (intent.getSerializableExtra(MEME_EXTRA)) as? MemeEntry
        meme?.let {
            binding.meme = it
        }
    }
}
