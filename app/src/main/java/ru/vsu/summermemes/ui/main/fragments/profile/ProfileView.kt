package ru.vsu.summermemes.ui.main.fragments.profile

import com.arellomobile.mvp.MvpView
import ru.vsu.summermemes.data.db.entities.MemeEntity

interface ProfileView : MvpView {
    fun showMemes(memeEntities: List<MemeEntity>)

    fun hideMemesList()

    fun showLoadingError()

    fun showLoading()

    fun hideLoading()
}