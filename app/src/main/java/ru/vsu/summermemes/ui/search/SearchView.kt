package ru.vsu.summermemes.ui.search

import ru.vsu.summermemes.ui.main.fragments.base.MemeListView

interface SearchView: MemeListView {
    fun showNothingFoundMessage()

    fun hideNothingFoundMessage()
}