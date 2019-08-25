package ru.vsu.summermemes.ui.main.fragments.profile


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import ru.vsu.summermemes.R
import ru.vsu.summermemes.data.sharedprefs.repositories.UserRepository
import ru.vsu.summermemes.databinding.ActivityMemeDetailBinding
import ru.vsu.summermemes.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    companion object {
        const val IMAGE_URL = "https://static1.squarespace.com/static/58c89af95016e18d70555dca/58d8827f14fd83a16d060663/5b513bfd70a6ade9ec7d5aac/1532051074596/dmitriy-ilkevich-441481-unsplash.jpg?format=1500w"
    }

    val userRepository = UserRepository()

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.imageUrl = IMAGE_URL
        binding.userInfo = userRepository.getUserInfo()
        return binding.root
    }

}
