package ru.vsu.summermemes.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_new_meme.*
import kotlinx.android.synthetic.main.toolbar_main_activity.*
import ru.vsu.summermemes.R
import ru.vsu.summermemes.ui.main.fragments.feed.FeedFragment
import ru.vsu.summermemes.ui.main.fragments.profile.ProfileFragment
import ru.vsu.summermemes.ui.newmeme.NewMemeActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val FEED_TAG = "FEED"
        const val PROFILE_TAG = "PROFILE"
    }

    private lateinit var fragmentManager: FragmentManager

    private val feedFragment by lazy {
        FeedFragment()
    }

    private val profileFragment by lazy {
        ProfileFragment()
    }

    private var lastFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar_main))
        setContentView(R.layout.activity_main)
        configureFragmentManager()
        configureFragmentNavigation()
    }

    private fun configureFragmentManager() {
        fragmentManager = supportFragmentManager
    }

    private fun configureFragmentNavigation() {
        main_bottom_nav_menu.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_feed -> {
                    setupFeedToolbar()
                    showFragment(feedFragment, FEED_TAG)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_add -> {
                    openNewMemeActivity()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    setupProfileToolbar()
                    showFragment(profileFragment, PROFILE_TAG)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }

        main_bottom_nav_menu.selectedItemId = R.id.navigation_feed
    }

    private fun setupProfileToolbar() {
        toolbar_content.visibility = View.GONE
        toolbar_main.inflateMenu(R.menu.profile_menu)
    }

    private fun setupFeedToolbar() {
        toolbar_content.visibility = View.VISIBLE
        toolbar_main.menu.clear()
    }

    private fun openNewMemeActivity() {
        val intent = Intent(this, NewMemeActivity::class.java)
        startActivity(intent)
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        val fragmentTransaction = fragmentManager.beginTransaction()

        lastFragment?.let {
            fragmentTransaction.hide(it)
        }

        if (fragmentManager.findFragmentByTag(tag) != null)
            fragmentTransaction.show(fragment)
        else
            fragmentTransaction.add(R.id.fragment_container, fragment, tag)

        lastFragment = fragment

        fragmentTransaction.commit()
    }
}
