package ru.vsu.summermemes.ui.main.fragments.base.activity

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.transition.Fade
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.arellomobile.mvp.MvpAppCompatActivity
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.ui.memedetail.MemeDetailActivity
import ru.vsu.summermemes.utils.image.TmpImageStorage

abstract class MemeListActivity : MvpAppCompatActivity(), MemeListActivityView {

    override fun openMemeDetailActivity(meme: MemeEntity, imageView: ImageView) {
        val intent = Intent(this, MemeDetailActivity::class.java)
        intent.putExtra(MemeDetailActivity.MEME_EXTRA, meme)

        configureTransition()

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            imageView,
            ViewCompat.getTransitionName(imageView)!!
        )

        TmpImageStorage.image = (imageView.drawable as BitmapDrawable).bitmap
        startActivity(intent, options.toBundle())
    }

    private fun configureTransition() {
        val fade = Fade()
        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)

        window.enterTransition = fade
        window.exitTransition = fade
    }


}