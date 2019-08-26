package ru.vsu.summermemes.ui.memedetail

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_meme_detail.*
import kotlinx.android.synthetic.main.toolbar_meme_detail_activity.*
import ru.vsu.summermemes.R
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.databinding.ActivityMemeDetailBinding
import ru.vsu.summermemes.utils.date.DateConvertHelper
import ru.vsu.summermemes.utils.image.BitmapConvertHelper
import ru.vsu.summermemes.utils.image.GlideImageLoader

class MemeDetailActivity : AppCompatActivity() {

    companion object {
        const val MEME_EXTRA = "MEME"
        const val IMAGE_MEME_EXTRA = "IMAGE_BITMAP"
    }

    var memeEntity: MemeEntity? = null
    var imageBitmap: Bitmap? = null

    private lateinit var binding: ActivityMemeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meme_detail)

        memeEntity = (intent.getSerializableExtra(MEME_EXTRA)) as? MemeEntity
        val imageByteArray = (intent.getSerializableExtra(IMAGE_MEME_EXTRA)) as? ByteArray
        imageByteArray?.let {
            imageBitmap = BitmapConvertHelper.getBitmapFromByteArray(imageByteArray)
        }
        initUI()
    }

    private fun initUI() {
        configureCloseButton()
        memeEntity?.let { memeEntity ->
            binding.memeEntity = memeEntity
            if (memeEntity.meme.description.isEmpty()) {
                hideDescription()
            }
            meme_date_created.text = DateConvertHelper.getDaysAgoCreated(memeEntity.meme.createdDate)
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
//        memeEntity?.meme?.photoUrl?.let {
//            GlideImageLoader.loadImage(it, meme_image)
//        }
    }
}
