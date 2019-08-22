package ru.vsu.summermemes.ui.main.fragments.feed


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.vsu.summermemes.R
import ru.vsu.summermemes.api.repositories.MemeRepository

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MemeRepository()
            .getMemes()
            .subscribe(
                { memesList ->
                    print(memesList.count())
                },
                {
                    print("ooh")
                }
            )
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

}
