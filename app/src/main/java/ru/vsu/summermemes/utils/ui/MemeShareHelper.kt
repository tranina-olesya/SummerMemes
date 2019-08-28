package ru.vsu.summermemes.utils.ui

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import ru.vsu.summermemes.data.db.entities.MemeEntity
import java.io.File
import javax.inject.Inject

class MemeShareHelper @Inject constructor(val context: Context) {
    fun shareMemeIntent(memeEntity: MemeEntity): Intent {
        return if (memeEntity.meme.photoUrl != null) {
            getIntentForMemeFromAPI(memeEntity)
        } else {
            getIntentForLocalMeme(memeEntity)
        }
    }

    private fun getIntentForLocalMeme(memeEntity: MemeEntity): Intent {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "image/*"
            addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
            putExtra(Intent.EXTRA_SUBJECT, "Поделиться мемом")

            val message = "${memeEntity.meme.title}\n${memeEntity.meme.description}\n"
            putExtra(Intent.EXTRA_TEXT, message)
            val uri = FileProvider.getUriForFile(
                context,
                "ru.vsu.summermemes.provider",
                File(memeEntity.imagePath)
            )
            putExtra(Intent.EXTRA_STREAM, uri)
        }
        return sendIntent
    }

    private fun getIntentForMemeFromAPI(memeEntity: MemeEntity): Intent {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
            putExtra(Intent.EXTRA_SUBJECT, "Поделиться мемом")
            val message =
                "${memeEntity.meme.title}\n${memeEntity.meme.description}\n${memeEntity.meme.photoUrl}"
            putExtra(Intent.EXTRA_TEXT, message)
        }
        return sendIntent
    }
}