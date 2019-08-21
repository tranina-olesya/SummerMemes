package ru.vsu.summermemes.ui.authorization

import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
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

    override fun initUI() {
        configurePasswordTextFiledBoxes()
        configureLoginButton()
    }

    private fun configurePasswordTextFiledBoxes() {
        password_edit_text.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        password_edit_text.typeface = Typeface.DEFAULT
        configurePasswordValidation()
        configurePasswordHideIcon()
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
}
