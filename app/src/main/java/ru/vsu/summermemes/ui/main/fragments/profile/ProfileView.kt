package ru.vsu.summermemes.ui.main.fragments.profile

import com.arellomobile.mvp.MvpView
import ru.vsu.summermemes.data.db.entities.MemeEntity
import ru.vsu.summermemes.models.auth.UserInfo
import ru.vsu.summermemes.models.meme.MemeEntry

interface ProfileView : MvpView {
    fun showMemes(memeEntities: List<MemeEntity>)

    fun hideMemesList()

    fun showLoadingError()

    fun showLoading()

    fun hideLoading()

    fun openMemeDetailActivity(meme: MemeEntity, byteArray: ByteArray?)

    fun setupBinding(imageUrl: String, userInfo: UserInfo?)
}