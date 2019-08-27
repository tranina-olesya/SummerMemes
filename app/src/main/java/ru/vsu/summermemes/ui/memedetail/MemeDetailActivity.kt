package ru.vsu.summermemes.ui.memedetail

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_meme_detail.*
import kotlinx.android.synthetic.main.toolbar_meme_detail_activity.*
import ru.vsu.summermemes.R
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.databinding.ActivityMemeDetailBinding
import ru.vsu.summermemes.utils.image.GlideImageLoader
import ru.vsu.summermemes.utils.image.TmpImageStorage

class MemeDetailActivity : MvpAppCompatActivity(), MemeDetailView {

    companion object {
        const val MEME_EXTRA = "MEME"
    }

    private lateinit var binding: ActivityMemeDetailBinding

    @InjectPresenter
    lateinit var presenter: MemeDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meme_detail)
        loadValuesFromIntent()

        initUI()
        presenter.viewIsReady()
    }

    override fun hideDescription() {
        meme_description.visibility = View.GONE
    }

    override fun initMeme(memeEntity: MemeEntity, date: String) {
        binding.memeEntity = memeEntity
        meme_date_created.text = date
    }

    override fun setImageFromBitmap(bitmap: Bitmap) {
        meme_image.setImageBitmap(bitmap)
    }

    override fun loadImageFromURL(url: String) {
        GlideImageLoader.loadImage(url, meme_image)
    }

    override fun loadImageFromFiles(uri: String) {
        GlideImageLoader.loadImageFromFiles(uri, meme_image)
    }

    private fun loadValuesFromIntent() {
        presenter.imageBitmap = TmpImageStorage.image
        TmpImageStorage.image = null

        presenter.memeEntity = (intent.getSerializableExtra(MEME_EXTRA)) as? MemeEntity
    }

    private fun initUI() {
        configureCloseButton()
    }


    private fun configureCloseButton() {
        toolbar_close_button.setOnClickListener {
            onBackPressed()
        }
    }
}
