package ru.vsu.summermemes.ui.newmeme.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_fragment_add_image.*
import ru.vsu.summermemes.R
import ru.vsu.summermemes.ui.newmeme.NewMemeActivity

class AddImageDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_add_image, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
    }

    private fun initUI() {
        configureCameraButton()
        configureGalleryButton()
    }

    private fun configureCameraButton() {
        make_photo_button.setOnClickListener {
            (activity as? NewMemeActivity)?.cameraButtonClicked()
            dismiss()
        }
    }

    private fun configureGalleryButton() {
        gallery_button.setOnClickListener {
            (activity as? NewMemeActivity)?.galleryButtonClicked()
            dismiss()
        }
    }
}