package ru.vsu.summermemes.ui.main.fragments.base

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.transition.Fade
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.toolbar_main_activity.*
import ru.vsu.summermemes.R
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.ui.memedetail.MemeDetailActivity
import ru.vsu.summermemes.utils.image.TmpImageStorage


abstract class MemeListFragment : MvpAppCompatFragment(), MemeListView {

    override fun showLoadingErrorOnTopOfContent(parentView: View) {
        activity?.let {
            val snackbar =
                Snackbar.make(parentView, R.string.refresh_memes_error, Snackbar.LENGTH_LONG)

            snackbar.view.setBackgroundColor(ContextCompat.getColor(it, R.color.error))
            val textView =
                snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text) as? TextView
            textView?.setTextColor(ContextCompat.getColor(it, R.color.white))

            snackbar.show()
        }
    }

    override fun openMemeDetailActivity(meme: MemeEntity,imageView: ImageView) {
        activity?.apply {
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
    }

    override fun shareMeme(intent: Intent) {
        activity?.apply {
            startActivity(intent)
        }
    }

    private fun FragmentActivity.configureTransition() {
        val fade = Fade()
        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)

        window.enterTransition = fade
        window.exitTransition = fade
    }
}