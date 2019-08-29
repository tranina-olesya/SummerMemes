package ru.vsu.summermemes.ui.main.fragments.base.fragment

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.MvpAppCompatFragment
import com.google.android.material.snackbar.Snackbar
import ru.vsu.summermemes.R
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.ui.main.fragments.base.MemeListView
import ru.vsu.summermemes.ui.main.fragments.base.activity.MemeListActivityView


abstract class MemeListFragment : MvpAppCompatFragment(),
    MemeListView {

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

    override fun openMemeDetailActivity(meme: MemeEntity, imageView: ImageView) {
        (activity as? MemeListActivityView)?.apply {
            this.openMemeDetailActivity(meme, imageView)
        }
    }

    override fun shareMeme(intent: Intent) {
        activity?.apply {
            startActivity(intent)
        }
    }
}