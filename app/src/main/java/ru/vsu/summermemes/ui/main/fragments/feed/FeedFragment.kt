package ru.vsu.summermemes.ui.main.fragments.feed


import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_feed.*
import ru.vsu.summermemes.R
import ru.vsu.summermemes.models.meme.MemeEntry
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

    override fun onStart() {
        super.onStart()
        initUI()
        presenter.viewIsReady()
    }

    override fun showMemesList(memes: List<MemeEntry>) {
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
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun openMemeDetailActivity(meme: MemeEntry, byteArray: ByteArray?) {
        activity?.let {
            val intent = Intent(activity, MemeDetailActivity::class.java)
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
                StaggeredGridLayoutManager(COLUMNS_COUNT, LinearLayoutManager.VERTICAL)
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
