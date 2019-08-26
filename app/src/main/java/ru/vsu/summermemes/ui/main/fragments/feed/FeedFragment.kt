package ru.vsu.summermemes.ui.main.fragments.feed


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_feed.*
import ru.vsu.summermemes.R
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.models.meme.MemeEntry
import ru.vsu.summermemes.ui.main.base.FeedAdapter
import ru.vsu.summermemes.ui.memedetail.MemeDetailActivity

class FeedFragment : MvpAppCompatFragment(), FeedView {

    companion object {
        const val COLUMNS_COUNT = 2
    }

    private var feedAdapter: FeedAdapter? = null

    @InjectPresenter
    lateinit var presenter: FeedPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
        presenter.viewIsReady()
    }

    override fun showMemesList(memes: List<MemeEntity>) {
        recycler_view.visibility = View.VISIBLE

        feedAdapter ?: configureRecyclerView()
        feedAdapter?.memeList = memes
    }

    override fun hideMemesList() {
        recycler_view.visibility = View.GONE
    }

    override fun showLoadingError() {
        error_text_view.visibility = View.VISIBLE
    }

    override fun hideLoadingError() {
        error_text_view.visibility = View.GONE
    }

    override fun showLoading() {
        progress_view.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_view.visibility = View.GONE
    }

    override fun showLoadingErrorOnTopOfContent() {
        activity?.let {
            val snackbar =
                Snackbar.make(parent_view, R.string.refresh_memes_error, Snackbar.LENGTH_LONG)

            snackbar.view.setBackgroundColor(ContextCompat.getColor(it, R.color.error))
            val textView =
                snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text) as? TextView
            textView?.setTextColor(ContextCompat.getColor(it, R.color.white))

            snackbar.show()
        }
    }

    override fun openMemeDetailActivity(meme: MemeEntry, byteArray: ByteArray?) {
        activity?.apply {
            val intent = Intent(this, MemeDetailActivity::class.java)
            intent.putExtra(MemeDetailActivity.MEME_EXTRA, meme)
            intent.putExtra(MemeDetailActivity.IMAGE_MEME_EXTRA, byteArray)
            startActivity(intent)
        }
    }

    private fun initUI() {
        configureRecyclerView()
        configureSwipeRefreshLayout()
        hideLoading()
        hideLoadingError()
        hideMemesList()
    }

    private fun configureRecyclerView() {
        activity?.let {
            feedAdapter = FeedAdapter(it, presenter)
            val layoutManager =
                androidx.recyclerview.widget.StaggeredGridLayoutManager(
                    COLUMNS_COUNT,
                    LinearLayoutManager.VERTICAL
                )
            recycler_view.layoutManager = layoutManager
            recycler_view.adapter = feedAdapter
        }
    }

    private fun configureSwipeRefreshLayout() {
        swipe_refresh_layout.setProgressBackgroundColorSchemeResource(R.color.active)
        swipe_refresh_layout.setOnRefreshListener {
            swipe_refresh_layout.isRefreshing = false
            presenter.refreshMemes()
        }
    }
}
