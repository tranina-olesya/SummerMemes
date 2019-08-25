package ru.vsu.summermemes.ui.newmeme

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_meme.*
import kotlinx.android.synthetic.main.toolbar_new_meme.*
import ru.vsu.summermemes.R

class NewMemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_meme)
        initUI()
    }

    private fun initUI() {
        configureAddImageButton()
        configureCloseButton()
        configureDeleteImageButton()
        hideMemeImage()
        updateButtonEnabledState()
        configureTitleEditText()
    }

    private fun configureAddImageButton() {
        add_image_button.setOnClickListener {
            showMemeImage()
            meme_image.setImageResource(R.drawable.surf_edu)
            updateButtonEnabledState()
        }
    }

    private fun configureTitleEditText() {
        meme_title_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateButtonEnabledState()
            }

        })
    }

    private fun showMemeImage() {
        image_container.visibility = View.VISIBLE
    }

    private fun hideMemeImage() {
        image_container.visibility = View.GONE
    }

    private fun updateButtonEnabledState() {
        meme_title_edit_text.text ?: return
        create_meme_button.isEnabled =
            meme_title_edit_text.text!!.isNotEmpty() && meme_image.drawable != null
    }

    private fun configureCloseButton() {
        toolbar_close_button.setOnClickListener {
            finish()
        }
    }

    private fun configureDeleteImageButton() {
        delete_image.setOnClickListener {
            meme_image.setImageDrawable(null)
            hideMemeImage()
            updateButtonEnabledState()
        }
    }
}
