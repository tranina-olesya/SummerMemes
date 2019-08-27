package ru.vsu.summermemes.ui.main.fragments.feed

import com.arellomobile.mvp.MvpView
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.ui.main.fragments.base.MemeListView

interface FeedView : MemeListView {
    fun showLoadingError()

    fun hideLoadingError()
}