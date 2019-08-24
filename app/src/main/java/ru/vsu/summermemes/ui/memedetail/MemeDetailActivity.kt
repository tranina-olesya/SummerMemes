package ru.vsu.summermemes.ui.memedetail

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_meme_detail.*
import ru.vsu.summermemes.R
import ru.vsu.summermemes.databinding.ActivityMemeDetailBinding
import ru.vsu.summermemes.models.meme.MemeEntry
import ru.vsu.summermemes.utils.DateConvertHelper

class MemeDetailActivity : AppCompatActivity() {

    companion object {
        const val MEME_EXTRA = "MEME"
    }

    var meme: MemeEntry? = null

    private lateinit var binding: ActivityMemeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meme_detail)

        meme = (intent.getSerializableExtra(MEME_EXTRA)) as? MemeEntry
        initUI()
    }

    private fun initUI() {
        meme?.let { meme ->
            binding.meme = meme
            if (meme.description.isEmpty()) {
                hideDescription()
            }
            meme_date_created.text = DateConvertHelper.getDaysAgoCreated(meme.createdDate)
        }
    }

    private fun hideDescription() {
        meme_description.visibility = View.GONE
    }
}
