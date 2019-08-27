package ru.vsu.summermemes.ui.main.fragments.profile

import ru.vsu.summermemes.models.auth.UserInfo
import ru.vsu.summermemes.ui.main.fragments.base.MemeListView

interface ProfileView : MemeListView {
    fun setupBinding(imageUrl: String, userInfo: UserInfo?)

    fun notifyMemeDeleted(position: Int)
}