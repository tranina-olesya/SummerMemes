package ru.vsu.summermemes.models.meme

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MemeInfo(
    @ColumnInfo(name = "apiId")
    @SerializedName("id")
    val apiId: Long?,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,
    @ColumnInfo(name = "description")
    @SerializedName("description")
    val description: String,
    @ColumnInfo(name = "isFavorite")
    @SerializedName("isFavorite")
    var isFavorite: Boolean,
    @ColumnInfo(name = "createdDate")
    @SerializedName("createdDate")
    val createdDate: Long,
    @ColumnInfo(name = "photoUrl")
    @SerializedName("photoUtl")
    val photoUrl: String?
) : Serializable