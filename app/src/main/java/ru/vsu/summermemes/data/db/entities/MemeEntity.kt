package ru.vsu.summermemes.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vsu.summermemes.models.meme.MemeEntry
import java.io.Serializable

@Entity(tableName = "memes")
class MemeEntity : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    @ColumnInfo(name = "image_path")
    var imagePath: String? = null

    @Embedded
    lateinit var meme: MemeEntry
}