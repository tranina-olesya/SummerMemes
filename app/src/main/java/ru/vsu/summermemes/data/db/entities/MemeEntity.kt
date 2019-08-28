package ru.vsu.summermemes.data.db.entities

import androidx.room.*
import ru.vsu.summermemes.models.meme.MemeInfo
import java.io.Serializable

@Entity(
    tableName = "memes",
    indices = [Index(
        value = ["title", "description", "photoUrl", "createdDate"],
        unique = true
    )]
)
class MemeEntity : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null

    @ColumnInfo(name = "isLocal")
    var isLocal = false

    @ColumnInfo(name = "imagePath")
    var imagePath: String? = null

    @Embedded
    lateinit var meme: MemeInfo
}