package ru.vsu.summermemes.ui.memedetail

import androidx.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_meme_detail.*
import kotlinx.android.synthetic.main.toolbar_meme_detail_activity.*
import ru.vsu.summermemes.R
import ru.vsu.summermemes.databinding.ActivityMemeDetailBinding
import ru.vsu.summermemes.models.meme.MemeEntry
import ru.vsu.summermemes.utils.BitmapConvertHelper
import ru.vsu.summermemes.utils.DateConvertHelper
import ru.vsu.summermemes.utils.GlideImageLoader

class MemeDetailActivity : AppCompatActivity() {

    companion object {
        const val MEME_EXTRA = "MEME"
        const val IMAGE_MEME_EXTRA = "IMAGE_BITMAP"
    }

    var meme: MemeEntry? = null
    var imageBitmap: Bitmap? = null

    private lateinit var binding: ActivityMemeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meme_detail)

        meme = (intent.getSerializableExtra(MEME_EXTRA)) as? MemeEntry
        val imageByteArray = (intent.getSerializableExtra(IMAGE_MEME_EXTRA)) as? ByteArray
        imageByteArray?.let {
            imageBitmap = BitmapConvertHelper.getBitmapFromByteArray(imageByteArray)
        }
        initUI()
    }

    private fun initUI() {
        configureCloseButton()
        meme?.let { meme ->
            binding.meme = meme
            if (meme.description.isEmpty()) {
                hideDescription()
            }
            meme_date_created.text = DateConvertHelper.getDaysAgoCreated(meme.createdDate)
            setMemeImage()
        }
    }

    private fun hideDescription() {
        meme_description.visibility = View.GONE
    }

    private fun configureCloseButton() {
        toolbar_close_button.setOnClickListener {
            finish()
        }
    }

    private fun setMemeImage() {
        if (imageBitmap != null)
            meme_image.setImageBitmap(imageBitmap)
        else
            downloadImage()
    }

    private fun downloadImage() {
        meme?.let { meme ->
            GlideImageLoader.loadImage(meme.photoUrl, meme_image)
        }
    }
}
