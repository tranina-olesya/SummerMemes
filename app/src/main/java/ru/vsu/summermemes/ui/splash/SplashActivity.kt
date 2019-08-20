package ru.vsu.summermemes.ui.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import ru.vsu.summermemes.R
import ru.vsu.summermemes.ui.authorization.AuthorizationActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initializeHandler()
    }

    private fun initializeHandler() {
        Handler().postDelayed({
            openNewActivity()
            finish()
        }, SPLASH_TIME_OUT)
    }

    private fun openNewActivity() {
        val intent = Intent(this, AuthorizationActivity::class.java)
        startActivity(intent)
    }
}
