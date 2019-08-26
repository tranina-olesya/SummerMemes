package ru.vsu.summermemes.ui.main.fragments.profile


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
import ru.vsu.summermemes.data.sharedprefs.repositories.UserRepository
import ru.vsu.summermemes.databinding.FragmentProfileBinding
import ru.vsu.summermemes.ui.main.base.FeedAdapter
import ru.vsu.summermemes.ui.main.fragments.feed.FeedFragment

class ProfileFragment : MvpAppCompatFragment(), ProfileView {

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    companion object {
        const val IMAGE_URL =
            "https://static1.squarespace.com/static/58c89af95016e18d70555dca/58d8827f14fd83a16d060663/5b513bfd70a6ade9ec7d5aac/1532051074596/dmitriy-ilkevich-441481-unsplash.jpg?format=1500w"
    }

    val userRepository = UserRepository()

    private var feedAdapter: FeedAdapter? = null

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        configureBinding()

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

    private fun configureBinding() {
        binding.imageUrl = IMAGE_URL
        binding.userInfo = userRepository.getUserInfo()
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

    fun memeChosen(memeEntity: MemeEntity) {

    }
}
