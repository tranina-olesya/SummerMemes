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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MemeEntity

        if (id != other.id) return false
        if (isLocal != other.isLocal) return false
        if (imagePath != other.imagePath) return false
        if (meme != other.meme) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + isLocal.hashCode()
        result = 31 * result + (imagePath?.hashCode() ?: 0)
        result = 31 * result + meme.hashCode()
        return result
    }
}