package ru.vsu.summermemes.ui.authorization

import android.app.Activity
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
import kotlinx.android.synthetic.main.activity_authorization.*
import ru.vsu.summermemes.R
import ru.vsu.summermemes.models.LoginRequestEntity

class AuthorizationActivity : MvpAppCompatActivity(), AuthorizationView {

    companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    @InjectPresenter
    lateinit var presenter: AuthorizationPresenter

    var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)
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
            if (isPasswordVisible) {
                password_edit_text.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                password_edit_text.typeface = Typeface.DEFAULT
                password_text_field_boxes.setEndIcon(R.drawable.ic_eye_on)
            } else {
                password_edit_text.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                password_text_field_boxes.setEndIcon(R.drawable.ic_eye_off)
            }
            password_edit_text.setSelection(password_edit_text.text.length)
            isPasswordVisible = !isPasswordVisible
        }
    }

    private fun configureLoginButton() {
        login_button.setOnClickListener {
            val login = login_edit_text.text.toString()
            val password = password_edit_text.text.toString()
            presenter.loginButtonClicked(LoginRequestEntity(login, password))
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
        login_button.text = ""
        login_button.isEnabled = false
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
        login_button.text = getString(R.string.login_button)
        login_button.isEnabled = true
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
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

    override fun showLoginError() {
        val snackbar = Snackbar.make(parent_view, R.string.login_error, Snackbar.LENGTH_LONG)

        snackbar.view.setBackgroundColor(ContextCompat.getColor(this@AuthorizationActivity, R.color.error))
        val textView = snackbar.view.findViewById(android.support.design.R.id.snackbar_text) as? TextView
        textView?.setTextColor(ContextCompat.getColor(this@AuthorizationActivity, R.color.white))

        snackbar.show()
    }
}
