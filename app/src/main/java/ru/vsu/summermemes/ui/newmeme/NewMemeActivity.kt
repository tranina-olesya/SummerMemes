package ru.vsu.summermemes.ui.newmeme

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import ru.vsu.summermemes.R

import kotlinx.android.synthetic.main.activity_new_meme.*

class NewMemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_meme)
    }

}
