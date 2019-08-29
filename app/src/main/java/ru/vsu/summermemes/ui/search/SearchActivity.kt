package ru.vsu.summermemes.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.toolbar_search_activity.*
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList
import ru.vsu.summermemes.R
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.ui.main.fragments.feed.FeedFragment
import ru.vsu.summermemes.ui.search.controllers.MemeController
import java.util.*
import java.util.concurrent.TimeUnit

class SearchActivity : MvpAppCompatActivity(), SearchView {

    @InjectPresenter
    lateinit var presenter: SearchPresenter

    private var subscribtion: Disposable? = null

    private val adapter = EasyAdapter()

    private lateinit var memeController: MemeController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initUI()
    }

    override fun showMemesList(memes: List<MemeEntity>) {
        val itemList = ItemList.create()
            .addAll(memes, memeController)
        adapter.setItems(itemList)
    }

    override fun hideMemesList() {
    }

    override fun showLoadingErrorOnTopOfContent(parentView: View) {
    }

    override fun showLoadingErrorOnTopOfContent() {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun openMemeDetailActivity(meme: MemeEntity, imageView: ImageView) {
    }

    override fun updateElement(meme: MemeEntity, position: Int) {
    }

    override fun shareMeme(intent: Intent) {
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        configureClearButton()
        configureSearchView()
        configureRecyclerView()
    }

    private fun configureClearButton() {
        toolbar_clear_button.setOnClickListener {
            meme_name_search_view.text.clear()
        }
    }

    private fun configureSearchView() {
        subscribtion = Observable
            .create(ObservableOnSubscribe<String> { subscriber ->
                meme_name_search_view.addTextChangedListener(object :
                    TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        subscriber.onNext(s.toString())
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }

                })
            })
            .map { text -> text.toLowerCase(Locale.getDefault()).trim() }
            .debounce(1000, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .filter { text -> text.isNotBlank() }
            .subscribe { text -> presenter.searchMeme(text) }
    }

    private fun configureRecyclerView() {
        val layoutManager =
            androidx.recyclerview.widget.StaggeredGridLayoutManager(
                FeedFragment.COLUMNS_COUNT,
                LinearLayoutManager.VERTICAL
            )
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter
        configureMemeController()
    }

    private fun configureMemeController() {
        memeController = MemeController({ memeEntity, imageView ->
            presenter.memeChosen(memeEntity, imageView)
        }, {
//            presenter.favoriteButtonPressed(meme)
        }, {
            presenter.shareMeme(it)
        })
    }
}
