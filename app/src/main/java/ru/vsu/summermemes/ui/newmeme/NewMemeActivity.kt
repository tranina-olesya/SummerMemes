package ru.vsu.summermemes.ui.newmeme

import android.app.Activity
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_new_meme.*
import kotlinx.android.synthetic.main.toolbar_new_meme.*
import ru.vsu.summermemes.R
import ru.vsu.summermemes.ui.newmeme.fragments.AddImageDialogFragment

class NewMemeActivity : MvpAppCompatActivity(), NewMemeView {
    companion object {
        const val MAX_TITLE_LENGTH = 140
        const val MAX_DESCRIPTION_LENGTH = 1000
    }

    @InjectPresenter
    lateinit var presenter: NewMemePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_meme)

        initUI()
    }

    override fun showErrorSavingMeme() {
        val snackbar = Snackbar.make(parent_view, R.string.meme_save_error, Snackbar.LENGTH_LONG)

        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.error))
        val textView =
            snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text) as? TextView
        textView?.setTextColor(ContextCompat.getColor(this, R.color.white))

        snackbar.show()
    }

    override fun closeActivity() {
        finish()
    }

    private fun initUI() {
        configureEditTexts()

        configureAddImageButton()
        configureCloseButton()
        configureDeleteImageButton()
        configureCreateMemeButton()

        configureHidingKeyboard()

        hideMemeImage()
    }

    private fun configureHidingKeyboard() {
        parent_view.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus)
                hideKeyboard(view)
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun configureEditTexts() {
        meme_title_text_input_layout.counterMaxLength = MAX_TITLE_LENGTH
        meme_description_text_input_layout.counterMaxLength = MAX_DESCRIPTION_LENGTH
        configureTitleEditText()
    }

    private fun configureAddImageButton() {
        add_image_button.setOnClickListener {
            val dialog = AddImageDialogFragment()
            dialog.show(supportFragmentManager, "")

            showMemeImage()
            meme_image.setImageResource(R.drawable.surf_edu)

            presenter.image = (meme_image.drawable as BitmapDrawable).bitmap
        }
    }

    private fun configureTitleEditText() {
        meme_title_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.title = s.toString()
            }
        })
    }

    private fun showMemeImage() {
        image_container.visibility = View.VISIBLE
    }

    private fun hideMemeImage() {
        image_container.visibility = View.GONE
    }

    override fun isCreateEnabled(isEnabled: Boolean) {
        create_meme_button.isEnabled = isEnabled
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
            presenter.image = null
        }
    }

    private fun configureCreateMemeButton() {
        create_meme_button.setOnClickListener {
            val title = meme_title_edit_text.text.toString()
            val description = meme_description_edit_text.text.toString()
            val bitmap = (meme_image.drawable as? BitmapDrawable)?.bitmap
            presenter.saveButtonPressed(title, description, bitmap)
        }
    }
}
