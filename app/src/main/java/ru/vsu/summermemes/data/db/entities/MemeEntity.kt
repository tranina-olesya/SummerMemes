package ru.vsu.summermemes.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.vsu.summermemes.models.meme.MemeEntry

@Entity(tableName = "memes")
class MemeEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    @ColumnInfo(name = "image_path")
    var imagePath: String? = null

    @Embedded
    lateinit var meme: MemeEntry
}