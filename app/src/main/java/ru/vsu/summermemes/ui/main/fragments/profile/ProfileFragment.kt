package ru.vsu.summermemes.ui.main.fragments.profile


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_profile.*
import ru.vsu.summermemes.R
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.databinding.FragmentProfileBinding
import ru.vsu.summermemes.models.auth.UserInfo
import ru.vsu.summermemes.ui.base.FeedAdapter
import ru.vsu.summermemes.ui.main.fragments.feed.FeedFragment
import ru.vsu.summermemes.ui.memedetail.MemeDetailActivity

class ProfileFragment : MvpAppCompatFragment(), ProfileView {

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    private var feedAdapter: FeedAdapter? = null

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        presenter.viewIsReady()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
    }

    override fun setupBinding(imageUrl: String, userInfo: UserInfo?) {
        binding.imageUrl = imageUrl
        binding.userInfo = userInfo
    }

    private fun initUI() {
        configureRecyclerView()
        hideMemesList()
        hideLoading()
    }

    private fun configureRecyclerView() {
        activity?.let {
            feedAdapter = FeedAdapter(it, presenter)
            val layoutManager =
                androidx.recyclerview.widget.StaggeredGridLayoutManager(
                    FeedFragment.COLUMNS_COUNT,
                    LinearLayoutManager.VERTICAL
                )
            recycler_view.layoutManager = layoutManager
            recycler_view.adapter = feedAdapter
        }
    }


    override fun showMemes(memeEntities: List<MemeEntity>) {
        recycler_view.visibility = View.VISIBLE

        feedAdapter ?: configureRecyclerView()
        feedAdapter?.memeList = memeEntities
    }

    override fun hideMemesList() {
        recycler_view.visibility = View.GONE
    }

    override fun showLoadingError() {
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun openMemeDetailActivity(meme: MemeEntity, byteArray: ByteArray?) {
        activity?.apply {
            val intent = Intent(this, MemeDetailActivity::class.java)
            intent.putExtra(MemeDetailActivity.MEME_EXTRA, meme)
            intent.putExtra(MemeDetailActivity.IMAGE_MEME_EXTRA, byteArray)
            startActivity(intent)
        }
    }
}
