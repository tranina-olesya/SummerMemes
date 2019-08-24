package ru.vsu.summermemes.data.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vsu.summermemes.models.meme.MemeEntry

@Entity(tableName = "memes")
class MemeEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    @Embedded(prefix = "meme_")
    lateinit var meme: MemeEntry
}