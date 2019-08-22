package ru.vsu.summermemes.ui.auth

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_auth.*
import ru.vsu.summermemes.R
import ru.vsu.summermemes.data.sharedprefs.repositories.UserRepository
import ru.vsu.summermemes.models.AuthRequestEntity
import ru.vsu.summermemes.ui.main.MainActivity

class AuthActivity : MvpAppCompatActivity(), AuthView {

    companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    @InjectPresenter
    lateinit var presenter: AuthPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        initUI()
        hideLoading()
    }

    private fun initUI() {
        configurePasswordTextFiledBoxes()
        configureLoginTextFiledBoxes()
        configureLoginButton()
        configureHidingKeyboard()
    }

    private fun configurePasswordTextFiledBoxes() {
        password_edit_text.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        password_edit_text.typeface = Typeface.DEFAULT
        configurePasswordValidation()
        configurePasswordHideIcon()
        configurePasswordKeyboard()
    }

    private fun configureLoginTextFiledBoxes() {
        configureLoginKeyboard()
    }

    private fun configurePasswordValidation() {
        password_edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if (s.length < MIN_PASSWORD_LENGTH)
                        password_text_field_boxes.helperText =
                            String.format(getString(R.string.password_helper), MIN_PASSWORD_LENGTH)
                    else
                        password_text_field_boxes.helperText = ""
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun configurePasswordHideIcon() {
        password_text_field_boxes.endIconImageButton.setOnClickListener {
            presenter.passwordHideButtonClicked()
        }
    }

    override fun showPassword() {
        password_edit_text.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        password_text_field_boxes.setEndIcon(R.drawable.ic_eye_off)
        password_edit_text.setSelection(password_edit_text.text.length)
    }

    override fun hidePassword() {
        password_edit_text.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        password_edit_text.typeface = Typeface.DEFAULT
        password_text_field_boxes.setEndIcon(R.drawable.ic_eye_on)
        password_edit_text.setSelection(password_edit_text.text.length)
    }

    private fun configureLoginButton() {
        auth_button.setOnClickListener {
            val login = login_edit_text.text.toString()
            val password = password_edit_text.text.toString()
            presenter.loginButtonClicked(AuthRequestEntity(login, password))
        }
    }

    override fun showErrorForEmptyLogin() {
        login_text_field_boxes.setError(getString(R.string.field_empty_error), false)
    }

    override fun showErrorForEmptyPassword() {
        password_text_field_boxes.setError(getString(R.string.field_empty_error), false)
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
        auth_button.text = ""
        auth_button.isEnabled = false
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
        auth_button.text = getString(R.string.auth_button)
        auth_button.isEnabled = true
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, 0)
    }

    private fun configurePasswordKeyboard() {
        password_edit_text.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus)
                showKeyboard(view)
        }
    }

    private fun configureLoginKeyboard() {
        login_edit_text.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus)
                showKeyboard(view)
        }
    }

    private fun configureHidingKeyboard() {
        parent_view.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus)
                hideKeyboard(view)
        }
    }

    override fun showAuthError() {
        val snackbar = Snackbar.make(parent_view, R.string.auth_error, Snackbar.LENGTH_LONG)

        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.error))
        val textView =
            snackbar.view.findViewById(android.support.design.R.id.snackbar_text) as? TextView
        textView?.setTextColor(ContextCompat.getColor(this, R.color.white))

        snackbar.show()
    }

    override fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
