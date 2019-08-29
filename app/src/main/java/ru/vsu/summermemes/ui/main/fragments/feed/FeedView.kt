package ru.vsu.summermemes.ui.main.fragments.feed

import ru.vsu.summermemes.ui.main.fragments.base.MemeListView

interface FeedView : MemeListView {
    fun showLoadingError()

    fun hideLoadingError()
}