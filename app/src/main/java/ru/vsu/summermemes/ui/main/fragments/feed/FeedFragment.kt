package ru.vsu.summermemes.ui.main.fragments.feed


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_feed.*
import ru.vsu.summermemes.R
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.ui.main.fragments.base.FeedAdapter
import ru.vsu.summermemes.ui.main.fragments.base.MemeListFragment

class FeedFragment : MemeListFragment(), FeedView {

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
        showLoadingErrorOnTopOfContent(parent_view)
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
            feedAdapter = FeedAdapter(it)
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
